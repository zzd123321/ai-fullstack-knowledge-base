from server.states import ComicDramaState


def revision_stalled(state: ComicDramaState) -> dict[str, object]:
    return {"events": [f"修订停滞：{state['stop_reason']}"]}
