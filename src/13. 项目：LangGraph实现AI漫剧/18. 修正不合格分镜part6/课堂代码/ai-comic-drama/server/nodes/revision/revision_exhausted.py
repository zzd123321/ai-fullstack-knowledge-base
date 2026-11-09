from server.states import ComicDramaState


def revision_exhausted(state: ComicDramaState) -> dict[str, object]:
    return {
        "current_stage": "revision_exhausted",
        "stop_reason": "max_revisions_reached",
        "events": [f"已达到 {state['max_revisions']} 次修订上限"],
    }
