import json
import logging
import re
from typing import Any

from arkruntime import Ark

from server.prompts import (
    CHARACTER_PROMPT,
    QUALITY_PROMPT,
    SCENE_PROMPT,
    STORY_PROMPT,
    STORYBOARD_PROMPT,
    SYSTEM_PROMPT,
)
from server.schema import (
    CharacterList,
    CharacterProfile,
    CreativeRequirement,
    ModelQualityReport,
    SceneList,
    SceneProfile,
    StoryBoard,
    StoryBoardList,
    StoryboardQualityReport,
    StoryOutline,
)


def normalize_quality_report(
    report: ModelQualityReport, known_ids: list[str]
) -> StoryboardQualityReport:
    """精确 ID 优先；仅修正唯一的纯数字后缀格式差异，不按 order 猜测。"""
    known = set(known_ids)
    numeric_ids: dict[int, list[str]] = {}
    for shot_id in known:
        match = re.fullmatch(r"shot_([0-9]+)", shot_id)
        if match:
            numeric_ids.setdefault(int(match.group(1)), []).append(shot_id)

    issues = []
    corrections = []
    for issue in report.issues:
        original = issue.shot_id
        if original is None or original in known:
            issues.append(issue)
            continue
        match = re.fullmatch(r"shot_([0-9]+)", original)
        candidates = numeric_ids.get(int(match.group(1)), []) if match else []
        if len(candidates) != 1:
            raise ValueError(
                f"质量报告引用无法唯一匹配的分镜：{original}；"
                f"候选={sorted(candidates)}；合法 ID={sorted(known)}"
            )
        corrected = candidates[0]
        issues.append(issue.model_copy(update={"shot_id": corrected}))
        corrections.append((original, corrected))

    result = report.model_copy(update={"issues": issues}).to_report()
    for original, corrected in corrections:
        logging.getLogger(__name__).warning(
            "质量报告镜头 ID 格式纠正：%s → %s", original, corrected
        )
    return result


class ArkTextModel:
    def __init__(self, *, api_key: str, model: str) -> None:
        self._client = Ark.volc(api_key=api_key)
        self._model = model

    def analyze_requirement(self, raw_requirement: str) -> CreativeRequirement:
        completion = self._client.beta.chat.completions.parse(
            model=self._model,
            messages=[
                {"role": "system", "content": SYSTEM_PROMPT},
                {"role": "user", "content": raw_requirement},
            ],
            response_format=CreativeRequirement,
        )

        requirement = completion.choices[0].message.parsed

        if requirement is None:
            raise ValueError("文本模型没有返回可解析的结构化需求")

        # 返回图的 requirement 这一项状态
        return requirement

    # 也是去新增 TextModel 里面定义的那 3 个方法

    # 多增加了一个辅助方法 _parse，就相当于把调模型的方法提取出来了
    # 因为回头新增的那 3 个方法都会用到
    def _parse(self, *, system: str, payload: dict[str, Any], schema: type[Any]) -> Any:
        # 调用模型
        completion = self._client.beta.chat.completions.parse(
            model=self._model,  # 指定模型
            # 给模型传递消息
            messages=[
                {"role": "system", "content": system},
                {"role": "user", "content": json.dumps(payload, ensure_ascii=False)},
            ],
            response_format=schema,
        )

        parsed = completion.choices[0].message.parsed

        if parsed is None:
            raise ValueError("文本模型没有返回可解析的策划结果")

        return parsed

    # 故事大纲
    def generate_story_outline(self, requirement: CreativeRequirement) -> StoryOutline:
        return self._parse(
            system=STORY_PROMPT,
            payload={"requirement": requirement.model_dump(mode="json")},
            schema=StoryOutline,
        )

    # 生成角色
    def generate_characters(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[CharacterProfile]:
        result = self._parse(
            system=CHARACTER_PROMPT,
            payload={
                "requirement": requirement.model_dump(mode="json"),
                "story_outline": outline.model_dump(mode="json"),
            },
            schema=CharacterList,
        )
        return result.characters

    # 生成场景
    def generate_scenes(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[SceneProfile]:
        result = self._parse(
            system=SCENE_PROMPT,
            payload={
                "requirement": requirement.model_dump(mode="json"),
                "story_outline": outline.model_dump(mode="json"),
            },
            schema=SceneList,
        )
        return result.scenes

    def generate_storyboard(
        self,
        requirement: CreativeRequirement,
        outline: StoryOutline,
        characters: list[CharacterProfile],
        scenes: list[SceneProfile],
    ) -> list[StoryBoard]:
        result = self._parse(
            system=STORYBOARD_PROMPT,
            payload={
                "requirement": requirement.model_dump(mode="json"),
                "story_outline": outline.model_dump(mode="json"),
                "characters": [item.model_dump(mode="json") for item in characters],
                "scenes": [item.model_dump(mode="json") for item in scenes],
                "target_shot_count": requirement.shot_count,
                "target_duration_seconds": requirement.duration_seconds,
                "allowed_character_ids": [item.character_id for item in characters],
                "allowed_scene_ids": [item.scene_id for item in scenes],
            },
            schema=StoryBoardList,
        )

        # 返回图新的状态，主要就是更新图里面的 storyboard 这个状态
        return result.storyboard_list

    def evaluate_storyboard_quality(
        self,
        requirement: CreativeRequirement,
        outline: StoryOutline,
        characters: list[CharacterProfile],
        scenes: list[SceneProfile],
        storyboard: list[StoryBoard],
        previous_report: StoryboardQualityReport | None = None,
    ) -> StoryboardQualityReport:
        # 列表推导式：拿到分镜头的 id
        # ['shot_01', 'shot_02', 'shot_03', 'shot_04', 'shot_05']
        # known_ids 作用就是记录所有分镜头真实的分镜 id
        known_ids = [shot.shot_id for shot in storyboard]

        system = QUALITY_PROMPT + (
            "\n本次合法镜头 ID："
            + json.dumps(known_ids, ensure_ascii=False)
            + "。issues 中的 shot_id 必须逐字复制上述值，或使用 null 表示全局问题。"
            "ID 不是镜头序号，不得自行构造、删掉前导零或缩写。"
        )

        report = self._parse(
            system=system
            + (
                "\n这是修订后的复评：先逐项核对 previous_quality_report 中的问题。"
                "summary 简述旧问题已解决/仍存在/无法确认，再检查整稿新增问题。"
                "已解决的问题不再列入 issues；未解决与新增问题须有当前稿证据，"
                "沿用相同严重程度判据。旧报告只是待核对依据，不是正确性证明；"
                "不得为证明修订有用而放行，也不得为找问题而捏造新问题。"
                if previous_report is not None
                else ""
            ),
            payload={
                "requirement": requirement.model_dump(mode="json"),
                "story_outline": outline.model_dump(mode="json"),
                "characters": [item.model_dump(mode="json") for item in characters],
                "scenes": [item.model_dump(mode="json") for item in scenes],
                "storyboard": [item.model_dump(mode="json") for item in storyboard],
                "previous_quality_report": previous_report.model_dump(mode="json")
                if previous_report is not None
                else None,
            },
            schema=ModelQualityReport,
        )

        return normalize_quality_report(report, known_ids)
