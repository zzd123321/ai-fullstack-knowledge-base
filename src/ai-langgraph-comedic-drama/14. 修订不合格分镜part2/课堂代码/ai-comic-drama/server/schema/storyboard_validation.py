from pydantic import (
    BaseModel,
    ConfigDict,
)


class StoryboardValidation(BaseModel):
    """硬规则结果；失败时保留可定位的错误给后续修订节点。"""

    model_config = ConfigDict(extra="forbid")
    is_valid: bool
    errors: list[str]
