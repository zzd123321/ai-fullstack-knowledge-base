from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)


class CharacterProfile(BaseModel):
    """角色 ID 是跨分镜引用的稳定键，不依赖中文姓名。"""

    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)

    character_id: str = Field(pattern=r"^char_[a-z0-9_]+$", description="稳定角色 ID")
    name: str = Field(min_length=1, description="角色名")
    identity: str = Field(min_length=2, description="身份")
    personality: str = Field(min_length=2, description="性格")
    appearance: str = Field(min_length=2, description="稳定外观")
    clothing: str = Field(min_length=2, description="稳定服装")
    visual_keywords: list[str] = Field(
        min_length=1, description="图像提示词可复用的关键词"
    )
