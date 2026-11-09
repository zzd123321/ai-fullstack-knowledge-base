from typing import Literal

from pydantic import BaseModel, ConfigDict, Field, model_validator


# 表示的就是一条质量问题信息
# 这个不是写入图状态里面的
class QualityIssue(BaseModel):
    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)
    # 质量问题是属于哪种类型
    code: Literal[
        "hard_rule",
        "plot_gap",
        "character_inconsistent",
        "visual_unclear",
        "motion_conflict",
    ]
    severity: Literal["error", "warning"]  # 质量问题级别
    shot_id: str | None = Field(
        default=None, pattern=r"^shot_[a-z0-9_]+$"
    )  # 分镜头的 id
    description: str = Field(min_length=1)
    suggestion: str = Field(min_length=1)


# 这个才是回头会写入图里面的状态的结构
class StoryboardQualityReport(BaseModel):
    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)
    is_passed: bool  # 大模型审核分镜是否通过
    summary: str = Field(min_length=1)
    issues: list[QualityIssue]
    affected_shot_ids: list[str]  # 涉及到的分镜头 id

    @model_validator(mode="after")
    def check_consistency(self):
        # 第1个一致性验证
        blocking = any(issue.severity == "error" for issue in self.issues)
        # blocking：True （有 error） self.is_passed：True
        # blocking：False （没有 error） self.is_passed：False
        # blocking：True （有 error） self.is_passed：False （一致）
        if self.is_passed == blocking:
            raise ValueError("is_passed 必须与 error 级问题一致")

        # 第二个一致性验证
        # affected_shot_ids 必须和问题里的镜头对得上。
        expected = {issue.shot_id for issue in self.issues if issue.shot_id}
        if set(self.affected_shot_ids) != expected or len(
            self.affected_shot_ids
        ) != len(expected):
            raise ValueError("affected_shot_ids 必须与问题中的 shot_id 一致且不重复")
        
        # 校验通过，把当前这份报告交回去继续使用。
        return self
