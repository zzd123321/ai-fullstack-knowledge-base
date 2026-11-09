import operator
from typing import Annotated, NotRequired, TypedDict

from server.schema import (
    CharacterProfile,
    CreativeRequirement,
    SceneProfile,
    StoryOutline,
)


class ComicDramaState(TypedDict):
    """整张图"""

    # 用户最初的创意
    # 例如：深夜的办公室里，程序员小林正在修改一款漫画应用。零点到来时，他设计的漫画角色阿沐突然从屏幕中走了出来。
    raw_requirement: str

    # 结构化数据
    requirement: NotRequired[CreativeRequirement]

    story_outline: NotRequired[StoryOutline]
    characters: NotRequired[list[CharacterProfile]]
    scenes: NotRequired[list[SceneProfile]]

    # 记录当前所处的阶段
    # 例：首次输入 "submitted"；分镜待审 "quality_passed"；成片待审 "final_video_ready"；验收通过 "final_video_approved"
    current_stage: str

    # 记录当前经过了哪些阶段
    # ["已接收用户创意", "已生成结构化创意需求", "已生成故事大纲",]
    events: Annotated[list[str], operator.add]
