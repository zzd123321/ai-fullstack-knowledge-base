from server.model import TextModel
from server.states import ComicDramaState


def make_generate_story_outline(text_model: TextModel):
    def generate_story_outline(state: ComicDramaState) -> dict[str, object]:
        requirement = state.get("requirement")

        if requirement is None:
            raise ValueError("缺少 requirement，不能生成故事大纲")

        outline = text_model.generate_story_outline(requirement)

        return {
            "story_outline": outline,
            "current_stage": "story_ready",
            "events": ["已生成故事大纲"],
        }

    return generate_story_outline
