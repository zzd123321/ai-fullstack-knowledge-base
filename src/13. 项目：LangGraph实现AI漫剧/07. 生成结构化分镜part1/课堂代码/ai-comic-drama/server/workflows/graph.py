from langgraph.graph import END, START, StateGraph

from server.model import TextModel, build_text_model
from server.nodes import (
    finish_planning,
    make_analyze_requirement,
    make_generate_characters,
    make_generate_scenes,
    make_generate_story_outline,
    receive_idea,
)
from server.states import ComicDramaState


def build_graph(text_model: TextModel):
    # 创建图：加节点、连边
    builder = StateGraph(ComicDramaState)
    # 加节点
    builder.add_node("receive_idea", receive_idea)
    builder.add_node("make_analyze_requirement", make_analyze_requirement(text_model))
    builder.add_node("generate_story_outline", make_generate_story_outline(text_model))
    builder.add_node("generate_characters", make_generate_characters(text_model))
    builder.add_node("generate_scenes", make_generate_scenes(text_model))
    builder.add_node("finish_planning", finish_planning)


    # 连边
    builder.add_edge(START, "receive_idea")
    builder.add_edge("receive_idea", "make_analyze_requirement")
    builder.add_edge("make_analyze_requirement", "generate_story_outline")

    builder.add_edge("generate_story_outline", "generate_characters")
    builder.add_edge("generate_story_outline", "generate_scenes")

    builder.add_edge(["generate_characters", "generate_scenes"], "finish_planning")
    builder.add_edge("finish_planning", END)

    return builder.compile()


graph = build_graph(build_text_model())
