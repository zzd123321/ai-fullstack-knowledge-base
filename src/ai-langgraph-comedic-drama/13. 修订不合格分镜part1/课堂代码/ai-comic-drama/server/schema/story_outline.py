from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)


class StoryOutline(BaseModel):
    """分镜前先确定故事因果，不包含镜头级动作。"""

    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)

    opening: str = Field(min_length=5, description="开端")
    conflict: str = Field(min_length=5, description="主要冲突")
    development: str = Field(min_length=5, description="发展")
    turning_point: str = Field(min_length=5, description="转折")
    ending: str = Field(min_length=5, description="结局")
    theme: str = Field(min_length=2, description="主题表达")
