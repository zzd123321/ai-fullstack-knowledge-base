from server.states import ComicDramaState


def mark_planning_ready(state: ComicDramaState) -> dict[str, object]:
    """确认创意已经存在，并把流程推进到等待需求整理阶段。"""

    if state["current_stage"] != "idea_received":
        raise ValueError("只有已接收的创意才能进入需求整理阶段")

    return {
        "current_stage": "planning_ready",
        "events": ["已进入需求整理准备阶段"],
    }
