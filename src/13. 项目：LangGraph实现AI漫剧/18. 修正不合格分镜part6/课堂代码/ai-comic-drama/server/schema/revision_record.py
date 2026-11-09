from pydantic import BaseModel, ConfigDict, Field


class RevisionRecord(BaseModel):
    """一条修订记录对象的约束"""
    model_config = ConfigDict(extra="forbid")
    version: int = Field(ge=0)
    fingerprint: str = Field(min_length=16, max_length=16)
    source_codes: list[str]
    target_shot_ids: list[str]
