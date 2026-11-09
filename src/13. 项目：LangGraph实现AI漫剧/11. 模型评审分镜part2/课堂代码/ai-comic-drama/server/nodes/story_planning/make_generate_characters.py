from server.model import TextModel
from server.states import ComicDramaState


def make_generate_characters(text_model: TextModel):
    def generate_characters(state: ComicDramaState) -> dict[str, object]:
        requirement = state.get("requirement")
        outline = state.get("story_outline")

        if requirement is None or outline is None:
            raise ValueError("缺少需求或故事大纲，不能生成角色设定")

        return {
            "characters": text_model.generate_characters(requirement, outline),
            "events": ["已生成角色设定"],
        }

    return generate_characters
