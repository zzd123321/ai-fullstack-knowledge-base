# 路由节点（条件边）
# 根据分镜修订之后的结果，决定跳转到哪里

from typing import Literal

from server.states import ComicDramaState


def route_after_revision(
    state: ComicDramaState,
) -> Literal["validate_revised_storyboard", "revision_stalled"]:
    return "revision_stalled" if state["stop_reason"] else "validate_revised_storyboard"
