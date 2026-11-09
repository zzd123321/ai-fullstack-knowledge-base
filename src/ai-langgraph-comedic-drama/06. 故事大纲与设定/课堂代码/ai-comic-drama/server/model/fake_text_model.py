from typing import Any

from server.schema import (
    CharacterProfile,
    CreativeRequirement,
    SceneProfile,
    StoryOutline,
)


# 假模型，返回模拟的数据
class FakeTextModel:
    # planning_fault 是报错参数，对应的环境变量里面的 FAKE_PLANNING_FAULT
    def __init__(
        self, response: dict[str, Any] | None = None, *, planning_fault: str = "none"
    ) -> None:
        self._response = response
        self._planning_fault = planning_fault

    def analyze_requirement(self, raw_requirement: str) -> CreativeRequirement:
        response = self._response or {
            "title": "零点之后",
            "genre": "都市奇幻",
            "target_audience": "喜欢悬疑反转的年轻观众",
            "duration_seconds": 45,
            "shot_count": 5,
            "visual_style": "冷暖对比的二维漫画",
            "aspect_ratio": "9:16",
            "core_idea": raw_requirement,
            "constraints": [],
        }

        return CreativeRequirement.model_validate(response)

    # 生成故事大纲
    def generate_story_outline(self, requirement: CreativeRequirement) -> StoryOutline:
        return StoryOutline(
            opening="小林在深夜办公室加班，漫画角色阿沐突然出现在屏幕旁。",
            conflict="阿沐必须在零点前回到漫画，否则会从故事中消失。",
            development="小林和阿沐寻找被遗落的最后一页，逐渐理解彼此的孤独。",
            turning_point="小林发现最后一页需要由自己画出，才能让故事继续。",
            ending="零点钟声响起，阿沐回到漫画，留下两人重逢的约定。",
            theme="用创作连接两个世界，也让孤独的人获得陪伴。",
        )

    # 生成角色
    def generate_characters(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[CharacterProfile]:
        if self._planning_fault == "characters":
            raise ValueError("Fake 角色分支故意失败")

        return [
            CharacterProfile(
                character_id="char_xiaolin",
                name="小林",
                identity="深夜加班的程序员",
                personality="认真但有些孤独",
                appearance="短发，眼下略有倦意",
                clothing="浅灰衬衫与深色长裤",
                visual_keywords=["短发", "浅灰衬衫"],
            ),
            CharacterProfile(
                character_id="char_amu",
                name="阿沐",
                identity="从漫画中走出的女孩",
                personality="勇敢而温柔",
                appearance="黑色长发，明亮的眼睛",
                clothing="暖黄色外套与白色裙子",
                visual_keywords=["黑色长发", "暖黄色外套"],
            ),
        ]

    # 生成场景
    def generate_scenes(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[SceneProfile]:
        if self._planning_fault == "scenes":
            raise ValueError("Fake 场景分支故意失败")

        return [
            SceneProfile(
                scene_id="scene_office",
                name="深夜办公室",
                time="午夜前后",
                location="电脑桌与落地窗相邻的开放办公区",
                lighting="台灯暖光与窗外冷光交错",
                colors="暖黄和深蓝",
                visual_keywords=["二维漫画", "暖黄台灯", "深蓝夜景"],
            )
        ]
