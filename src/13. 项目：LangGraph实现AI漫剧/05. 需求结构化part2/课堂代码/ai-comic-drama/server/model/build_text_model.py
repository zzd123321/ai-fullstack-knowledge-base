# 根据环境变量的配置来创建一个模型

import os

from .ark_text_model import ArkTextModel
from .fake_text_model import FakeTextModel
from .text_model import TextModel


def build_text_model() -> TextModel:
    provider = os.getenv("TEXT_MODEL_PROVIDER", "fake").strip().lower()

    if provider == "fake":
        return FakeTextModel()

    if provider == "ark":
        api_key = os.getenv("ARK_API_KEY", "").strip()
        model = os.getenv("ARK_MODEL", "").strip()

        if not api_key or not model:
            raise RuntimeError("使用 ark 时必须配置 ARK_API_KEY 和 ARK_MODEL")
        
        return ArkTextModel(api_key=api_key, model=model)
    
    raise ValueError(f"不支持的 TEXT_MODEL_PROVIDER：{provider}")