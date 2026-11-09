# 对外暴露的接口
from typing import Protocol

from server.schema import (
    CharacterProfile,
    CreativeRequirement,
    SceneProfile,
    StoryOutline,
)


class TextModel(Protocol):
    def analyze_requirement(self, raw_requirement: str) -> CreativeRequirement:
        """把自然语言创意转换为经过校验的结构化需求。"""

    def generate_story_outline(self, requirement: CreativeRequirement) -> StoryOutline:
        """先固定故事因果。"""

    def generate_characters(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[CharacterProfile]:
        """基于同一大纲生成角色设定。"""

    def generate_scenes(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[SceneProfile]:
        """基于同一大纲生成场景设定。"""
