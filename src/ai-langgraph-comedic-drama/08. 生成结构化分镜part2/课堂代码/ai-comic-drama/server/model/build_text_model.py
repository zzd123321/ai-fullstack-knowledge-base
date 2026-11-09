# 根据环境变量的配置来创建一个模型

import os

from .ark_text_model import ArkTextModel
from .fake_text_model import FakeTextModel
from .text_model import TextModel


# 模型注入
# 根据环境变量的值，来决定使用真实模型还是fake模型
def build_text_model() -> TextModel:
    provider = os.getenv("TEXT_MODEL_PROVIDER", "fake").strip().lower()

    if provider == "fake":
        # 先去读取环境变量的 FAKE_PLANNING_FAULT 值
        planning_fault = os.getenv("FAKE_PLANNING_FAULT", "none").strip().lower()
        # 读取环境变量里面的 FAKE_STORYBOARD_FAULT
        storyboard_fault = os.getenv("FAKE_STORYBOARD_FAULT", "none").strip().lower()

        if planning_fault not in {"none", "characters", "scenes"}:
            raise ValueError(f"不支持的 FAKE_PLANNING_FAULT：{planning_fault}")

        if storyboard_fault not in {
            "none",
            "duplicate_id",
            "unknown_character",
            "duration_mismatch",
        }:
            raise ValueError(f"不支持的 FAKE_STORYBOARD_FAULT：{storyboard_fault}")

        return FakeTextModel(planning_fault=planning_fault, storyboard_fault=storyboard_fault)

    if provider == "ark":
        api_key = os.getenv("ARK_API_KEY", "").strip()
        model = os.getenv("ARK_MODEL", "").strip()

        if not api_key or not model:
            raise RuntimeError("使用 ark 时必须配置 ARK_API_KEY 和 ARK_MODEL")

        return ArkTextModel(api_key=api_key, model=model)

    raise ValueError(f"不支持的 TEXT_MODEL_PROVIDER：{provider}")
