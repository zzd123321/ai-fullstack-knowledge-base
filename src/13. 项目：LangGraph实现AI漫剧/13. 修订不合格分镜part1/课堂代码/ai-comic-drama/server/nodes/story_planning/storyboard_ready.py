from server.states import ComicDramaState


def storyboard_ready(state: ComicDramaState) -> dict[str, object]:
    return {"current_stage": "storyboard_ready", "events": ["分镜硬规则通过"]}
