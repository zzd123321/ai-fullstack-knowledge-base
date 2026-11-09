from server.schema import StoryboardValidation
from server.states import ComicDramaState


def validate_storyboard(state: ComicDramaState) -> dict[str, object]:
    # 从图的状态获取 requirement（结构化数据）和分镜（上一步生成的）
    requirement = state["requirement"]
    storyboard = state["storyboard"]

    expected_count = requirement.shot_count  # 期望的分镜数
    expected_seconds = requirement.duration_seconds  # 期望的视频时长

    # 角色以及场景的 id 列表
    character_ids = {item.character_id for item in state["characters"]}
    scene_ids = {item.scene_id for item in state["scenes"]}

    # 有错误，就把错误信息放到 errors 列表里面
    errors: list[str] = []

    # 后面就是各种验证
    if len(storyboard) != expected_count:
        errors.append(f"镜头数量应为 {expected_count}，实际为 {len(storyboard)}")

    ids = [shot.shot_id for shot in storyboard]
    if len(ids) != len(set(ids)):
        errors.append("shot_id 重复")

    # 拿到分镜的 order
    orders = [shot.order for shot in storyboard]
    if len(orders) != len(set(orders)):
        errors.append("镜头 order 重复")
    if sorted(orders) != list(range(1, expected_count + 1)):
        errors.append(f"镜头 order 应连续为 1～{expected_count}")

    # 拿到每一个 StoryBoard
    for shot in storyboard:
        if shot.scene_id not in scene_ids:
            errors.append(f"{shot.shot_id} 引用了不存在的场景 {shot.scene_id}")
        for character_id in shot.character_ids:
            if character_id not in character_ids:
                errors.append(f"{shot.shot_id} 引用了不存在的角色 {character_id}")
        if len(shot.character_ids) != len(set(shot.character_ids)):
            errors.append(f"{shot.shot_id} 的 character_ids 重复")
        if not 2 <= shot.duration_seconds <= 12:
            errors.append(f"{shot.shot_id} 时长必须在 2～12 秒之间")
        for field_name in ("start_frame_prompt", "end_frame_prompt", "video_prompt"):
            if not getattr(shot, field_name).strip():
                errors.append(f"{shot.shot_id} 缺少 {field_name}")

    # 计算所有分镜时长加起来的总时长
    total = sum(shot.duration_seconds for shot in storyboard)
    if total != expected_seconds:
        errors.append(f"镜头总时长应为 {expected_seconds} 秒，实际为 {total} 秒")

    # 组装 storyboard_validation 数据
    report = StoryboardValidation(is_valid=not errors, errors=errors)

    return {
        "storyboard_validation": report,
        "events": ["已完成分镜硬规则校验"],
    }
