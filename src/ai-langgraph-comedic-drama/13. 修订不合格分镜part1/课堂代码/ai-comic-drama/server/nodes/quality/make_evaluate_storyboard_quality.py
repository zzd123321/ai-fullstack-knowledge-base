from server.model import TextModel
from server.schema import StoryboardQualityReport
from server.states import ComicDramaState


def make_evaluate_storyboard_quality(text_model: TextModel):
    # 用到了闭包
    def evaluate_storyboard_quality(state: ComicDramaState) -> dict[str, object]:
        if not state["storyboard_validation"].is_valid:
            raise ValueError("硬规则未通过，不能调用语义评价")

        report = text_model.evaluate_storyboard_quality(
            state["requirement"],
            state["story_outline"],
            state["characters"],
            state["scenes"],
            state["storyboard"],
            previous_report=(
                StoryboardQualityReport.model_validate(state["previous_quality_report"])
                if state.get("previous_quality_report") is not None
                else None
            ),
        )

        report = StoryboardQualityReport.model_validate(report)

        # 拿到已有分镜的 id
        # ['shot_01', 'shot_02', 'shot_03', 'shot_04', 'shot_05']
        known_ids = {shot.shot_id for shot in state["storyboard"]}

        referenced = set(report.affected_shot_ids) | {
            issue.shot_id for issue in report.issues if issue.shot_id
        }
        unknown = referenced - known_ids
        if unknown:
            raise ValueError(f"质量报告引用了未知分镜：{sorted(unknown)}")
        
        return {"quality_report": report, "events": ["已完成分镜语义质量评价"]}

        

    return evaluate_storyboard_quality
