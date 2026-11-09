# 一张子图，负责创建故事的

from typing import Literal

from langgraph.graph import END, START, StateGraph

from server.model import TextModel
from server.nodes import (
    finish_planning,
    make_analyze_requirement,
    make_generate_characters,
    make_generate_scenes,
    make_generate_story_outline,
    make_generate_storyboard,
    receive_idea,
    storyboard_invalid,
    storyboard_ready,
    validate_storyboard,
)
from server.states import ComicDramaState


# 首先需要新增一个路由函数
def route_storyboard(
    state: ComicDramaState,
) -> Literal["storyboard_ready", "storyboard_invalid"]:
    return (
        "storyboard_ready"
        if state["storyboard_validation"].is_valid
        else "storyboard_invalid"
    )


def build_story_planning_graph(text_model: TextModel):
    # 创建图：加节点、连边
    builder = StateGraph(ComicDramaState)
    # 加节点
    builder.add_node("receive_idea", receive_idea)
    builder.add_node("make_analyze_requirement", make_analyze_requirement(text_model))
    builder.add_node("generate_story_outline", make_generate_story_outline(text_model))
    builder.add_node("generate_characters", make_generate_characters(text_model))
    builder.add_node("generate_scenes", make_generate_scenes(text_model))
    builder.add_node("finish_planning", finish_planning)
    builder.add_node("generate_storyboard", make_generate_storyboard(text_model))
    builder.add_node("validate_storyboard", validate_storyboard)
    builder.add_node("storyboard_ready", storyboard_ready)
    builder.add_node("storyboard_invalid", storyboard_invalid)

    # 连边
    builder.add_edge(START, "receive_idea")
    builder.add_edge("receive_idea", "make_analyze_requirement")
    builder.add_edge("make_analyze_requirement", "generate_story_outline")

    builder.add_edge("generate_story_outline", "generate_characters")
    builder.add_edge("generate_story_outline", "generate_scenes")

    builder.add_edge(["generate_characters", "generate_scenes"], "finish_planning")
    builder.add_edge("finish_planning", "generate_storyboard")
    builder.add_edge("generate_storyboard", "validate_storyboard")
    builder.add_conditional_edges("validate_storyboard", route_storyboard)
    builder.add_edge("storyboard_ready", END)
    builder.add_edge("storyboard_invalid", END)

    return builder.compile()
