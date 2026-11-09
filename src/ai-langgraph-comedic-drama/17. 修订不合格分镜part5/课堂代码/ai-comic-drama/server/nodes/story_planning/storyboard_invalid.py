from server.states import ComicDramaState


def storyboard_invalid(state: ComicDramaState) -> dict[str, object]:
    return {"current_stage": "storyboard_invalid", "events": ["分镜硬规则未通过"]}
