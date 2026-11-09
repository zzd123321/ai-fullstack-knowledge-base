from langgraph.graph import END, START, StateGraph

from server.nodes import mark_planning_ready, receive_idea
from server.states import ComicDramaState

# 创建图：加节点、连边
builder = StateGraph(ComicDramaState)
# 加节点
builder.add_node("receive_idea", receive_idea)
builder.add_node("mark_planning_ready", mark_planning_ready)
# 连边
builder.add_edge(START, "receive_idea")
builder.add_edge("receive_idea", "mark_planning_ready")
builder.add_edge("mark_planning_ready", END)

graph = builder.compile()