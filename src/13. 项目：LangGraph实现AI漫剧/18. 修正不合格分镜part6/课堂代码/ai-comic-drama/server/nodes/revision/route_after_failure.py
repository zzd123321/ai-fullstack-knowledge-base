# 也是一个路由节点，用于条件边
# 失败的时候的路由，这里所谓的失败，就是看有没有超过最大的修订次数

from typing import Literal

from server.states import ComicDramaState


def route_after_failure(
    state: ComicDramaState,
) -> Literal["revise_storyboard", "revision_exhausted"]:
    return (
        "revise_storyboard"
        if state["revision_count"] < state["max_revisions"]
        else "revision_exhausted"
    )
