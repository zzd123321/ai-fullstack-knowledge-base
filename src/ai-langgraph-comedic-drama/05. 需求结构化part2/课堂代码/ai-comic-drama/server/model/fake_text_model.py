from typing import Any

from server.schema import CreativeRequirement


# 假模型，返回模拟的数据
class FakeTextModel:
    def __init__(self, response: dict[str, Any] | None = None) -> None:
        self._response = response

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
