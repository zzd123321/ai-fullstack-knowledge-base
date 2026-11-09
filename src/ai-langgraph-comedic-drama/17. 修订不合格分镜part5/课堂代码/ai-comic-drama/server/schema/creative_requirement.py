from typing import Literal

from pydantic import BaseModel, ConfigDict, Field, model_validator


class CreativeRequirement(BaseModel):
    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)

    # 定义有哪些字段
    title: str = Field(min_length=1, max_length=60, description="暂定剧名")
    genre: str = Field(min_length=1, max_length=30, description="题材类型")
    target_audience: str = Field(min_length=1, max_length=50, description="目标观众")
    duration_seconds: int = Field(default=45, ge=30, le=60, description="成片时长")
    shot_count: int = Field(default=5, ge=4, le=6, description="计划镜头数")
    visual_style: str = Field(
        default="暖色二维漫画",
        min_length=1,
        max_length=50,
        description="统一画面风格",
    )
    aspect_ratio: Literal["9:16", "16:9", "1:1"] = Field(
        default="9:16",
        description="成片画幅",
    )
    core_idea: str = Field(
        min_length=10,
        max_length=500,
        description="保留关键人物、冲突与结局方向的创意摘要",
    )
    constraints: list[str] = Field(
        default_factory=list,
        max_length=8,
        description="用户明确提出的其他限制",
    )
    @model_validator(mode="after")
    def validate_video_duration_capacity(self):
        minimum = 2 * self.shot_count
        maximum = 12 * self.shot_count
        if not minimum <= self.duration_seconds <= maximum:
            raise ValueError(
                f"当前视频链路每镜支持 2～12 秒；"
                f"{self.shot_count} 镜的总时长必须在 "
                f"{minimum}～{maximum} 秒之间"
            )
        return self
