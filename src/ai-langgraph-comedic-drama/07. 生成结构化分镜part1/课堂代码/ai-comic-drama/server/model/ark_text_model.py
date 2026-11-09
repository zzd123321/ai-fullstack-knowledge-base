import json
from typing import Any

from arkruntime import Ark

from server.prompts import CHARACTER_PROMPT, SCENE_PROMPT, STORY_PROMPT, SYSTEM_PROMPT
from server.schema import (
    CharacterList,
    CharacterProfile,
    CreativeRequirement,
    SceneList,
    SceneProfile,
    StoryOutline,
)


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
