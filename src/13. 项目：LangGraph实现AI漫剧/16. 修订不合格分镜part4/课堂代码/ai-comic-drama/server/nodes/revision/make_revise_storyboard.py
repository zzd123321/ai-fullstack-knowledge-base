from server.model import TextModel
from server.schema import RevisionRecord, StoryBoard
from server.states import ComicDramaState

from .shot_fingerprint import shot_fingerprint


def make_revise_storyboard(text_model: TextModel):
    def revise_storyboard(state: ComicDramaState) -> dict[str, object]:
        """对分镜进行一个修订"""

        # 先拿到质量报告
        report = state["quality_report"]

        # 防御性代码
        if report is None or report.is_passed or not report.issues:
            raise ValueError("没有可执行的质量问题，不能修订")
        if state["revision_count"] >= state["max_revisions"]:
            raise ValueError("修订次数已达上限")

        # 拿到当前的分镜
        old_shots = state["storyboard"]
        # 拿到结构化数据
        requirement = state["requirement"]

        # candidate 拿到的是修订后的整版5镜
        candidate = [
            StoryBoard.model_validate(shot)
            # 当调用模型的revise_storyboard()方法的时候，拿到的就是完整的5镜（里面是包含了修订后的分镜的）
            for shot in text_model.revise_storyboard(
                requirement,
                state["story_outline"],
                state["characters"],
                state["scenes"],
                old_shots,
                report,
                revision_count=state["revision_count"],
            )
        ]

        # 这里是两个字典，其实就是一旧一新
        # 按 order 建索引
        # old_by_order = {
        #     1: <旧 shot_01>,
        #     2: <旧 shot_02>,
        #     3: <旧 shot_03>,   # action 还是「两人寻找…」
        #     4: <旧 shot_04>,
        #     5: <旧 shot_05>,
        # }

        # new_by_order = {
        #     1: <新 shot_01>,
        #     2: <新 shot_02>,
        #     3: <新 shot_03>,   # action 已加「已按质量报告修订：」
        #     4: <新 shot_04>,
        #     5: <新 shot_05>,
        # }
        # 为什么这里 order 是从 1 开始的？
        old_by_order = {shot.order: shot for shot in old_shots}
        new_by_order = {shot.order: shot for shot in candidate}

        # 一些准备工作
        # old_ids -> ["shot_01", "shot_02", "shot_03", "shot_04", "shot_05"]
        # 修订之前，分镜id的顺序，用来检查模型有没有改名、重排。
        old_ids = [shot.shot_id for shot in old_shots]

        # 从质检报告中取出要修订的分镜的id -> ["shot_01", "shot_02"]
        targets = set(report.affected_shot_ids)

        # 后面主要就是做一些检查工作
        if len(candidate) != requirement.shot_count:
            raise ValueError("修订不得改变镜头数量")

        if (
            sum(shot.duration_seconds for shot in candidate)
            != requirement.duration_seconds
        ):
            raise ValueError("修订不得改变目标总时长")

        # python 里面两个列表是可以直接 == 比较的
        # [1,2,3] == [1,2,3]
        if (
            len(set(old_ids)) == len(old_ids)
            and [s.shot_id for s in candidate] != old_ids
        ):
            raise ValueError("已有唯一 shot_id 必须保持稳定")

        if targets and any(
            old_by_order[order] != shot and old_by_order[order].shot_id not in targets
            for order, shot in new_by_order.items()
        ):
            raise ValueError("修订改动了报告未指出的镜头")

        # 检查已经完了
        # 说明这一次的修订，目前是没有问题的，接下来就要做版本的记录
        signature = shot_fingerprint(candidate)
        history = state["revision_history"]

        # 看这一次修订后的内容所生成的指纹，是否和之前某一次一致
        if signature in {record.fingerprint for record in history}:
            # 说明改回去了
            reason = (
                "no_change"
                if signature == shot_fingerprint(old_shots)
                else "repeated_version"
            )
            return {
                "stop_reason": reason,
                "current_stage": "revision_stalled",
                "events": ["修订没有产生新版本，工作流停止"],
            }

        # 修订次数+1，这个修订次数就可以作为版本号
        next_version = state["revision_count"] + 1

        # 返回新的状态
        return {
            "storyboard": candidate,
            "quality_report": None,
            "previous_quality_report": report.model_dump(mode="json"),
            "storyboard_validation": None,
            "revision_count": next_version,
            "revision_history": [
                *history,  # 先把之前的历史展开
                # 然后再加入本次的
                RevisionRecord(
                    version=next_version,
                    fingerprint=signature,
                    source_codes=sorted(
                        {issue.code for issue in report.issues}
                    ),  # 本版是为了修哪些问题类型
                    target_shot_ids=sorted(targets),  # 本版改了哪些镜
                ),
            ],
            "stop_reason": None,
            "current_stage": "storyboard_revised",
            "events": [f"已生成第 {next_version} 版修订分镜，等待重新校验"],
        }

    return revise_storyboard
