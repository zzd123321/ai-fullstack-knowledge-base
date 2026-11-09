from server.states import ComicDramaState


def quality_passed(state: ComicDramaState) -> dict[str, object]:
    return {
        "current_stage": "quality_passed",
        "events": ["分镜质量检查通过，尚待人工审核"],
    }
