from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)


class SceneProfile(BaseModel):
    """场景 ID 将用于分镜和参考图的关联。"""

    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)

    scene_id: str = Field(pattern=r"^scene_[a-z0-9_]+$", description="稳定场景 ID")
    name: str = Field(min_length=1, description="场景名")
    time: str = Field(min_length=2, description="故事中的时间")
    location: str = Field(min_length=2, description="空间布局")
    lighting: str = Field(min_length=2, description="光线")
    colors: str = Field(min_length=2, description="主色调")
    visual_keywords: list[str] = Field(
        min_length=1, description="图像提示词可复用的关键词"
    )
