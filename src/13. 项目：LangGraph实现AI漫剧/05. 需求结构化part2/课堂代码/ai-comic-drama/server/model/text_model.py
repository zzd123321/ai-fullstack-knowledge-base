# 对外暴露的接口
from typing import Protocol

from server.schema import CreativeRequirement


class TextModel(Protocol):
    def analyze_requirement(self, raw_requirement: str) -> CreativeRequirement:
        """把自然语言创意转换为经过校验的结构化需求。"""