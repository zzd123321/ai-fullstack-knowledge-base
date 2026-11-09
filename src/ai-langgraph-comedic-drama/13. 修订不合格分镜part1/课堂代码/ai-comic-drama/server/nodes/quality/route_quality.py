from typing import Literal

from server.states import ComicDramaState


def route_quality(
    state: ComicDramaState,
) -> Literal["quality_passed", "quality_failed"]:
    return "quality_passed" if state["quality_report"].is_passed else "quality_failed"
