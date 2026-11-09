# 主图（父图）

from langgraph.graph import END, START, StateGraph

from server.model import TextModel, build_text_model
from server.nodes import (
    hard_rules_failed,
    initialize_revision,
    make_evaluate_storyboard_quality,
    make_revise_storyboard,
    quality_failed,
    quality_passed,
    revision_exhausted,
    revision_stalled,
    route_after_failure,
    route_after_revision,
    route_quality,
    validate_storyboard,
)
from server.states import ComicDramaState
from server.workflows.story_planning import build_story_planning_graph


def build_graph(text_model: TextModel):
    # 创建子图
    planning_graph = build_story_planning_graph(text_model)

    # 新增一个节点
    def story_planning(state: ComicDramaState) -> dict[str, object]:
        # 调用子图
        child_result = planning_graph.invoke(state)

        return {
            **child_result,
            "events": child_result["events"][len(state["events"]) :],
        }

    builder = StateGraph(ComicDramaState)

    # 加点
    builder.add_node("story_planning", story_planning)
    builder.add_node("hard_rules_failed", hard_rules_failed)
    builder.add_node("quality_passed", quality_passed)
    builder.add_node("quality_failed", quality_failed)
    builder.add_node(
        "evaluate_storyboard_quality", make_evaluate_storyboard_quality(text_model)
    )
    builder.add_node("revise_storyboard", make_revise_storyboard(text_model))
    builder.add_node("validate_revised_storyboard", validate_storyboard)
    builder.add_node("revision_exhausted", revision_exhausted)
    builder.add_node("revision_stalled", revision_stalled)
    builder.add_node("initialize_revision", initialize_revision)

    # 连边
    builder.add_edge(START, "story_planning")
    builder.add_edge("story_planning", "initialize_revision")
    builder.add_conditional_edges(
        "initialize_revision",
        lambda state: (
            "evaluate_storyboard_quality"
            if state["storyboard_validation"].is_valid
            else "hard_rules_failed"
        ),
        path_map=["evaluate_storyboard_quality", "hard_rules_failed"],
    )
    builder.add_conditional_edges(
        "hard_rules_failed", route_after_failure
    )  # 根据修订最大次数有没有超过来决定走哪一边
    builder.add_conditional_edges(
        "evaluate_storyboard_quality", route_quality
    )  # 看模型评审分镜有没有通过
    builder.add_edge("quality_passed", END)
    builder.add_conditional_edges(
        "quality_failed", route_after_failure
    )  # 根据修订最大次数有没有超过来决定走哪一边
    builder.add_conditional_edges("revise_storyboard", route_after_revision)
    builder.add_conditional_edges(
        "validate_revised_storyboard",
        lambda state: (
            "evaluate_storyboard_quality"
            if state["storyboard_validation"].is_valid
            else "hard_rules_failed"
        ),
        path_map=["evaluate_storyboard_quality", "hard_rules_failed"],
    )
    builder.add_edge("revision_exhausted", END)
    builder.add_edge("revision_stalled", END)

    return builder.compile()


graph = build_graph(build_text_model())
