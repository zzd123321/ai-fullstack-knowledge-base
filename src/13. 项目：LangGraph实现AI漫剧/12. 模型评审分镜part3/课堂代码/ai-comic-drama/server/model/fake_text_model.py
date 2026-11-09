from typing import Any

from server.schema import (
    CharacterProfile,
    CreativeRequirement,
    QualityIssue,
    SceneProfile,
    StoryBoard,
    StoryboardQualityReport,
    StoryOutline,
)


# 假模型，返回模拟的数据
class FakeTextModel:
    # planning_fault 是报错参数，对应的环境变量里面的 FAKE_PLANNING_FAULT
    # storyboard_fault 也分镜的报错参数，同样来自环境变量里面的 FAKE_STORYBOARD_FAULT
    # quality_fault 是模型评审分镜的参数，同样也是来自于环境变量里面的 FAKE_QUALITY_FAULT
    def __init__(
        self,
        response: dict[str, Any] | None = None,
        *,
        planning_fault: str = "none",
        storyboard_fault: str = "none",
        quality_fault: str = "none",
    ) -> None:
        self._response = response
        self._planning_fault = planning_fault
        self._storyboard_fault = storyboard_fault
        self._quality_fault = quality_fault

    def analyze_requirement(self, raw_requirement: str) -> CreativeRequirement:
        response = self._response or {
            "title": "零点之后",
            "genre": "都市奇幻",
            "target_audience": "喜欢悬疑反转的年轻观众",
            "duration_seconds": 45,  # 视频总时长
            "shot_count": 5,
            "visual_style": "冷暖对比的二维漫画",
            "aspect_ratio": "9:16",
            "core_idea": raw_requirement,
            "constraints": [],
        }

        return CreativeRequirement.model_validate(response)

    # 生成故事大纲
    def generate_story_outline(self, requirement: CreativeRequirement) -> StoryOutline:
        return StoryOutline(
            opening="小林在深夜办公室加班，漫画角色阿沐突然出现在屏幕旁。",
            conflict="阿沐必须在零点前回到漫画，否则会从故事中消失。",
            development="小林和阿沐寻找被遗落的最后一页，逐渐理解彼此的孤独。",
            turning_point="小林发现最后一页需要由自己画出，才能让故事继续。",
            ending="零点钟声响起，阿沐回到漫画，留下两人重逢的约定。",
            theme="用创作连接两个世界，也让孤独的人获得陪伴。",
        )

    # 生成角色
    def generate_characters(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[CharacterProfile]:
        if self._planning_fault == "characters":
            raise ValueError("Fake 角色分支故意失败")

        return [
            CharacterProfile(
                character_id="char_xiaolin",
                name="小林",
                identity="深夜加班的程序员",
                personality="认真但有些孤独",
                appearance="短发，眼下略有倦意",
                clothing="浅灰衬衫与深色长裤",
                visual_keywords=["短发", "浅灰衬衫"],
            ),
            CharacterProfile(
                character_id="char_amu",
                name="阿沐",
                identity="从漫画中走出的女孩",
                personality="勇敢而温柔",
                appearance="黑色长发，明亮的眼睛",
                clothing="暖黄色外套与白色裙子",
                visual_keywords=["黑色长发", "暖黄色外套"],
            ),
        ]

    # 生成场景
    def generate_scenes(
        self, requirement: CreativeRequirement, outline: StoryOutline
    ) -> list[SceneProfile]:
        if self._planning_fault == "scenes":
            raise ValueError("Fake 场景分支故意失败")

        return [
            SceneProfile(
                scene_id="scene_office",
                name="深夜办公室",
                time="午夜前后",
                location="电脑桌与落地窗相邻的开放办公区",
                lighting="台灯暖光与窗外冷光交错",
                colors="暖黄和深蓝",
                visual_keywords=["二维漫画", "暖黄台灯", "深蓝夜景"],
            )
        ]

    def generate_storyboard(
        self,
        requirement: CreativeRequirement,
        outline: StoryOutline,
        characters: list[CharacterProfile],
        scenes: list[SceneProfile],
    ) -> list[StoryBoard]:
        """只引用已登记角色与场景，返回整版分镜。"""

        # moments 存储的是 5 个分镜不一样的地方
        # moments 里面每一项：action、start、end
        moments = [
            ("小林看到屏幕旁出现阿沐", "小林惊讶地抬头", "阿沐站到桌边"),
            ("阿沐解释零点前必须回去", "阿沐指向墙上的钟", "小林决定帮忙"),
            ("两人寻找漫画的最后一页", "两人在桌上翻找", "发现空白纸张"),
            ("小林画出能让故事继续的画面", "小林握笔停顿", "画纸上出现回家的路"),
            ("阿沐回到漫画并约定重逢", "阿沐走向发光书页", "书页合上留下约定"),
        ]

        # divmod(a, b) 一次算出商和余数
        # 视频总时长 / 分镜数
        # base_duration 每个分镜应该多长
        # remainder 余数
        # 45s / 5 = 9s
        # 46s / 5 = 9s ... 1
        base_duration, remainder = divmod(
            requirement.duration_seconds, requirement.shot_count
        )

        # storyboard -> list[ StoryBoard数据, StoryBoard数据, StoryBoard数据]
        storyboard = [
            StoryBoard(
                shot_id=f"shot_{index:02d}",
                order=index,
                # 当前这个分镜的时长 = base_duration + (index <= remainder)
                # 如果是 47 秒 / 5 镜：base_duration=9，remainder=2。后面赋值用了：
                # 10、10、9、9、9
                duration_seconds=base_duration + (index <= remainder),
                scene_id="scene_office",
                character_ids=["char_xiaolin", "char_amu"],
                action=action,
                composition="竖屏中景，人物与办公桌保持相对位置",
                dialogue="",
                camera_motion="缓慢推近",
                start_frame_prompt=f"暖色二维漫画，深夜办公室，{start}",
                end_frame_prompt=f"暖色二维漫画，深夜办公室，{end}",
                video_prompt=(
                    f"{base_duration + (index <= remainder)} 秒镜头："
                    f"{action}；镜头缓慢推近。"
                ),
            )
            for index, (action, start, end) in enumerate(moments, start=1)
        ]

        # 代码执行到这里，5个假分镜就有了
        # 根据环境变量的设置，需要调整分镜，故意破坏
        if self._storyboard_fault == "duplicate_id":
            # 不仅仅要有 5 个分镜，还需要模拟 id 重复的情况

            # 第 2 镜的 shot_id 改成和第 1 镜一样
            storyboard[1] = storyboard[1].model_copy(
                update={"shot_id": storyboard[0].shot_id}
            )
        elif self._storyboard_fault == "unknown_character":
            # 这里就需要模拟“引用了未知角色的情况”

            # 第 3 镜角色改成 char_missing
            storyboard[2] = storyboard[2].model_copy(
                update={"character_ids": ["char_missing"]}
            )
        elif self._storyboard_fault == "duration_mismatch":
            # 这里就需要模拟“时长不对”的情况

            # 第 4 镜（不够 4 镜就改最后一镜）时长 +3
            fault_index = min(3, len(storyboard) - 1)
            storyboard[fault_index] = storyboard[fault_index].model_copy(
                update={
                    "duration_seconds": storyboard[fault_index].duration_seconds + 3
                }
            )

        return storyboard

    def evaluate_storyboard_quality(
        self,
        requirement: CreativeRequirement,
        outline: StoryOutline,
        characters: list[CharacterProfile],
        scenes: list[SceneProfile],
        storyboard: list[StoryBoard],
        previous_report: StoryboardQualityReport | None = None,
    ) -> StoryboardQualityReport:
        """评价当前分镜，不修改分镜。"""
        # FAKE_QUALITY_FAULT=invalid_report 时进入。
        # 返回一份「长得像报告、实际校验不过」的裸 dict：
        # is_passed=True 却带 error，构造 StoryboardQualityReport 会失败。
        # 用来模拟模型输出脏数据。
        if self._quality_fault == "invalid_report":
            return {
                "is_passed": True,
                "summary": "错误报告",
                "issues": [
                    {
                        "code": "plot_gap",
                        "severity": "error",
                        "shot_id": "shot_03",
                        "description": "转场跳跃",
                        "suggestion": "补充过渡",
                    }
                ],
                "affected_shot_ids": ["shot_03"],
            }
        # FAKE_QUALITY_FAULT=plot_gap 时进入。
        # 返回合法但不通过的报告：第三镜情节断裂（plot_gap）。
        if self._quality_fault == "plot_gap":
            return StoryboardQualityReport(
                is_passed=False,
                summary="第三镜头与故事设定衔接不足",
                issues=[
                    QualityIssue(
                        code="plot_gap",
                        severity="error",
                        shot_id="shot_03",
                        description="第三镜头缺少寻找过程",
                        suggestion="交代寻找与发现的因果",
                    )
                ],
                affected_shot_ids=["shot_03"],
            )
        # FAKE_QUALITY_FAULT=character_inconsistent 时进入。
        # 返回合法但不通过的报告：第三镜阿沐服装和角色设定不一致。
        if self._quality_fault == "character_inconsistent":
            return StoryboardQualityReport(
                is_passed=False,
                summary="第三镜头角色外观与设定不一致",
                issues=[
                    QualityIssue(
                        code="character_inconsistent",
                        severity="error",
                        shot_id="shot_03",
                        description="阿沐在第三镜头换成了与设定不符的服装",
                        suggestion="改回角色设定中的暖黄色外套与白色裙子",
                    )
                ],
                affected_shot_ids=["shot_03"],
            )
        # FAKE_QUALITY_FAULT=visual_unclear 时进入。
        # 返回合法但不通过的报告：第三镜首帧写不清人物和办公桌的空间关系。
        if self._quality_fault == "visual_unclear":
            return StoryboardQualityReport(
                is_passed=False,
                summary="第三镜头首帧无法明确生成",
                issues=[
                    QualityIssue(
                        code="visual_unclear",
                        severity="error",
                        shot_id="shot_03",
                        description="首帧没有写出人物与办公桌的空间关系",
                        suggestion="在首帧补上人物位置和关键道具",
                    )
                ],
                affected_shot_ids=["shot_03"],
            )
        # FAKE_QUALITY_FAULT=motion_conflict 时进入。
        # 返回合法但不通过的报告：第三镜固定机位和跟拍指令互相矛盾。
        if self._quality_fault == "motion_conflict":
            return StoryboardQualityReport(
                is_passed=False,
                summary="第三镜头的动作与摄影机运动矛盾",
                issues=[
                    QualityIssue(
                        code="motion_conflict",
                        severity="error",
                        shot_id="shot_03",
                        description="camera_motion 要求固定机位，video_prompt 却要求摄影机跟拍",
                        suggestion="统一为固定机位，把人物走动只写进动作",
                    )
                ],
                affected_shot_ids=["shot_03"],
            )
        
        # 模拟大模型评审分镜通过的情况
        return StoryboardQualityReport(
            is_passed=True,
            summary=f"{len(storyboard)} 个镜头衔接清楚，可进入人工审核",
            issues=[],
            affected_shot_ids=[],
        )
