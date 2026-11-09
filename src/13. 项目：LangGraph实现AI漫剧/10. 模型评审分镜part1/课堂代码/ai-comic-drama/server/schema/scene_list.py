from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)

from .scene_profile import SceneProfile


class SceneList(BaseModel):
    model_config = ConfigDict(extra="forbid")
    scenes: list[SceneProfile] = Field(min_length=1)
