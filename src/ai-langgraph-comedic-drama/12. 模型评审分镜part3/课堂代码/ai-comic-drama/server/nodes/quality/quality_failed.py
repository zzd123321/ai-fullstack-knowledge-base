from server.states import ComicDramaState


def quality_failed(state: ComicDramaState) -> dict[str, object]:
    return {
        "current_stage": "quality_failed",
        "events": ["分镜语义质量未通过，等待后续处理"],
    }
