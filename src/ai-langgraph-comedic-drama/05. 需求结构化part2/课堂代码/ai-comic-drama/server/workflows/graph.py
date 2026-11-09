from langgraph.graph import END, START, StateGraph

from server.model import TextModel, build_text_model
from server.nodes import make_analyze_requirement, receive_idea
from server.states import ComicDramaState


def build_graph(text_model: TextModel):
    # 创建图：加节点、连边
    builder = StateGraph(ComicDramaState)
    # 加节点
    builder.add_node("receive_idea", receive_idea)
    builder.add_node("make_analyze_requirement", make_analyze_requirement(text_model))
    # 连边
    builder.add_edge(START, "receive_idea")
    builder.add_edge("receive_idea", "make_analyze_requirement")
    builder.add_edge("make_analyze_requirement", END)

    return builder.compile()
    
graph = build_graph(build_text_model())