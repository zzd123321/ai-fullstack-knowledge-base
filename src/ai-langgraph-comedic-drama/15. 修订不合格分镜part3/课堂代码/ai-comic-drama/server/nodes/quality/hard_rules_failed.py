import re

from server.schema import QualityIssue, StoryboardQualityReport
from server.states import ComicDramaState


def hard_rules_failed(state: ComicDramaState) -> dict[str, object]:
    """已有硬规则报告转成统一质量报告；此分支不调用模型。"""
    issues = []

    for error in state["storyboard_validation"].errors:
        match = re.search(r"\bshot_(?!id\b)[a-z0-9_]+\b", error)

        issues.append(
            QualityIssue(
                code="hard_rule",
                severity="error",
                shot_id=match.group() if match else None,
                description=error,
                suggestion="按硬规则报告修正分镜字段后重新检查。",
            )
        )

    return {
        "quality_report": StoryboardQualityReport(
            is_passed=False,
            summary="分镜未通过硬规则校验",
            issues=issues,
            affected_shot_ids=sorted({i.shot_id for i in issues if i.shot_id}),
        ),
        "current_stage": "quality_failed",
        "events": ["分镜质量检查未通过：硬规则失败，跳过模型评价"],
    }
