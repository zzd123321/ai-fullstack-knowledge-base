from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
)


class StoryBoard(BaseModel):
    """一个能按稳定 ID 关联后续素材与视频任务的镜头。"""

    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)
    shot_id: str = Field(pattern=r"^shot_[a-z0-9_]+$", description="稳定镜头 ID")
    order: int = Field(ge=1, description="从 1 开始的播放顺序")
    duration_seconds: int = Field(ge=2, le=12, description="镜头时长，单位秒")
    scene_id: str = Field(pattern=r"^scene_[a-z0-9_]+$", description="引用已登记场景")
    character_ids: list[str] = Field(description="引用已登记角色，可为空列表")
    action: str = Field(min_length=1, description="画面中发生的动作")
    composition: str = Field(min_length=1, description="主体位置与构图")
    dialogue: str = Field(description="台词或旁白，没有则为空字符串")
    camera_motion: str = Field(min_length=1, description="镜头运动或固定机位")
    start_frame_prompt: str = Field(min_length=1, description="生成静态首帧的提示词")
    end_frame_prompt: str = Field(min_length=1, description="生成静态尾帧的提示词")
    video_prompt: str = Field(min_length=1, description="生成首尾帧之间运动的提示词")
