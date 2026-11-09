from server.model import TextModel
from server.states import ComicDramaState


def make_analyze_requirement(text_model: TextModel):
    """通过闭包注入文本模型"""

    # 闭包
    def analyze_requirement(state: ComicDramaState) -> dict[str, object]:
        """把原始创意转换为经过 Pydantic 校验的需求契约。"""

        if state["current_stage"] != "idea_received":
            raise ValueError("需求分析只能在收到创意后执行")

        # 这就是接口的好处，不管是真模型还是假模型
        requirement = text_model.analyze_requirement(state["raw_requirement"])

        # 返回图新的状态
        return {
            "requirement": requirement,
            "current_stage": "requirement_ready",
            "events": ["已生成结构化创意需求"],
        }

    return analyze_requirement
