from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)

from .storyboard import StoryBoard


class StoryBoardList(BaseModel):
    """真实模型结构化输出需要顶层对象，State 只保存内部 shots。"""

    model_config = ConfigDict(extra="forbid")
    storyboard_list: list[StoryBoard] = Field(min_length=1)
