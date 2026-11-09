from server.states import ComicDramaState


# 对上一步的信息进行汇总，汇总之前，简单的做了一个检查
def finish_planning(state: ComicDramaState) -> dict[str, object]:
    characters = state.get("characters") or []  # 获取生成的角色设定
    scenes = state.get("scenes") or []  # 获取生成的场景设定

    if not characters or not scenes:
        raise ValueError("角色或场景设定为空，策划不能完成")

    character_ids = [
        item.character_id for item in characters
    ]  # ['char_xiaolin', 'char_xiaomu']
    scene_ids = [item.scene_id for item in scenes]

    if len(character_ids) != len(set(character_ids)):
        raise ValueError("角色 character_id 重复")

    if len(scene_ids) != len(set(scene_ids)):
        raise ValueError("场景 scene_id 重复")

    return {"current_stage": "planning_ready", "events": ["策划资料已汇合并通过检查"]}
