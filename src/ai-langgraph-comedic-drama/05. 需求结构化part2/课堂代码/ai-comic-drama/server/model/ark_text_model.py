from arkruntime import Ark

from server.prompts import SYSTEM_PROMPT
from server.schema import CreativeRequirement


class ArkTextModel:
    def __init__(self, *, api_key: str, model: str) -> None:
        self._client = Ark.volc(api_key=api_key)
        self._model = model

    def analyze_requirement(self, raw_requirement: str) -> CreativeRequirement:
        completion = self._client.beta.chat.completions.parse(
            model=self._model,
            messages=[
                {"role": "system", "content": SYSTEM_PROMPT},
                {"role": "user", "content": raw_requirement},
            ],
            response_format=CreativeRequirement,
        )

        requirement = completion.choices[0].message.parsed

        if requirement is None:
            raise ValueError("文本模型没有返回可解析的结构化需求")
        
        # 返回图的 requirement 这一项状态
        return requirement
