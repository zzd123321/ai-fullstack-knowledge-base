import operator
from typing import Annotated, NotRequired, TypedDict

from server.schema import (
    CharacterProfile,
    CreativeRequirement,
    RevisionRecord,
    SceneProfile,
    StoryBoard,
    StoryboardQualityReport,
    StoryboardValidation,
    StoryOutline,
)


class ComicDramaState(TypedDict):
    """整张图"""

    # 用户最初的创意
    # 例如：深夜的办公室里，程序员小林正在修改一款漫画应用。零点到来时，他设计的漫画角色阿沐突然从屏幕中走了出来。
    raw_requirement: str

    # 结构化数据
    requirement: NotRequired[CreativeRequirement]

    # 故事大纲
    story_outline: NotRequired[StoryOutline]
    # 角色设定（对应的是一个list列表）
    characters: NotRequired[list[CharacterProfile]]
    # 场景设定（对应的是一个list列表）
    scenes: NotRequired[list[SceneProfile]]

    # 分镜（对应的是一个list列表）
    storyboard: NotRequired[list[StoryBoard]]
    # 硬规则对分镜评审后的结果
    storyboard_validation: NotRequired[StoryboardValidation]

    # 存储模型评审分镜后的结果
    quality_report: NotRequired[StoryboardQualityReport]
    # 存储的是上一次模型对分镜的评价
    previous_quality_report: NotRequired[dict | None]

    # 修订的次数
    revision_count: NotRequired[int]
    # 最大能够修订的次数
    max_revisions: NotRequired[int]
    # 修订的历史记录
    revision_history: NotRequired[list[RevisionRecord]]
    # 停止下一次修订的原因（修订过关了、超过最大修订次数了、修订回之前的版本了）
    stop_reason: NotRequired[str | None]


    # 记录当前所处的阶段
    # 例：首次输入 "submitted"；分镜待审 "quality_passed"；成片待审 "final_video_ready"；验收通过 "final_video_approved"
    current_stage: str

    # 记录当前经过了哪些阶段
    # ["已接收用户创意", "已生成结构化创意需求", "已生成故事大纲",]
    events: Annotated[list[str], operator.add]
