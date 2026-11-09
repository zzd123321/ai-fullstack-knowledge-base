from server.model import TextModel
from server.states import ComicDramaState


def make_generate_scenes(text_model: TextModel):
    def generate_scenes(state: ComicDramaState) -> dict[str, object]:
        requirement = state.get("requirement")
        outline = state.get("story_outline")

        if requirement is None or outline is None:
            raise ValueError("缺少需求或故事大纲，不能生成场景设定")
        
        return {
            "scenes": text_model.generate_scenes(requirement, outline),
            "events": ["已生成场景设定"],
        }

    return generate_scenes
