from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)

from .character_profile import CharacterProfile


class CharacterList(BaseModel):
    """让方舟 parse 用一个顶层模型承载角色列表。"""

    model_config = ConfigDict(extra="forbid")
    characters: list[CharacterProfile] = Field(min_length=1)
