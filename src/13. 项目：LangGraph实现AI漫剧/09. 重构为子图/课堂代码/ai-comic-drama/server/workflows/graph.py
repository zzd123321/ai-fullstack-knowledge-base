# 主图（父图）

from langgraph.graph import END, START, StateGraph

from server.model import TextModel, build_text_model
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

    # 连边
    builder.add_edge(START, "story_planning")
    builder.add_edge("story_planning", END)

    return builder.compile()


graph = build_graph(build_text_model())