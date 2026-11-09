# 生成分镜节点
from server.model import TextModel
from server.states import ComicDramaState


def make_generate_storyboard(text_model: TextModel):
    def generate_storyboard(state: ComicDramaState) -> dict[str, object]:
        # 先拿图里面已有的状态
        requirement = state.get("requirement")
        outline = state.get("story_outline")
        characters = state.get("characters")
        scenes = state.get("scenes")

        if requirement is None or outline is None or not characters or not scenes:
            raise ValueError("缺少需求、大纲、角色或场景，不能生成分镜")

        storyboard = text_model.generate_storyboard(
            requirement, outline, characters, scenes
        )

        # 返回图新的状态
        return {
            "storyboard": storyboard,
            "current_stage": "storyboard_generated",
            "events": ["已生成结构化分镜"],
        }

    return generate_storyboard