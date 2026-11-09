# ModelQualityReport的意义

- `ModelQualityReport`：让 AI 填写的报告
- `StoryboardQualityReport`：图最终使用的正式报告

先看一下两个的区别：

```py
class StoryboardQualityReport(BaseModel):
    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)
    is_passed: bool
    summary: str = Field(min_length=1)
    issues: list[QualityIssue]
    affected_shot_ids: list[str]

    @model_validator(mode="after")
    def check_consistency(self):
        ...
```

```py
class ModelQualityReport(BaseModel):
    """模型只生成问题事实，正式通过判断由 Python 推导。"""

    model_config = ConfigDict(extra="forbid", str_strip_whitespace=True)
    summary: str = Field(min_length=1)
    issues: list[QualityIssue]

    def to_report(self) -> StoryboardQualityReport:
        ...
```

真实模型的 `evaluate_storyboard_quality` 方法：

```py
def evaluate_storyboard_quality(
        ...
    ) -> StoryboardQualityReport:
        ...
        report = self._parse(
            system=...
            payload=...
            schema=ModelQualityReport,
        )
        return normalize_quality_report(report, known_ids)
```

这里让 AI 生成 report 的时候，是先生成 `ModelQualityReport` 类型的 report，然后再通过 `normalize_quality_report` 方法转换成 `StoryboardQualityReport` 类型。



## 1. 为什么不直接让 AI 生成 `StoryboardQualityReport` ？

为什么要这么多此一举？

假设我们这么写：

```py
report = self._parse(    ...    schema=StoryboardQualityReport,)
```

那么 AI 就得自己生成：

```json
{
  "is_passed": false,
  "summary": "第三镜头存在剧情断层",
  "issues": [
    {
      "code": "plot_gap",
      "severity": "error",
      "shot_id": "shot_03",
      "description": "缺少寻找过程",
      "suggestion": "补充寻找过程"
    }
  ],
  "affected_shot_ids": ["shot_03"]
}
```

这里产生了冗余信息。因为 `is_passed` 字段和 `affected_shot_ids` 字段，是可以根据 `issues` 字段计算出来的。

如果这部分让 AI 来生成，反而可能生成诸如：

```json
{
  "is_passed": true,
  "issues": [
    {
      "severity": "error",
      "shot_id": "shot_03"
    }
  ],
  "affected_shot_ids": ["shot_02"]
}
```

这种前后矛盾的信息。

所以我们干脆不让 AI 生成这些能够确定性计算的字段。

>在让 AI 生成内容的时候，只让 AI 生成无法通过规则确定的内容，凡是能够通过规则确定的内容，都应该通过编码来得到。





## 2. 那 `normalize_quality_report()` 又是干什么的？

`normalize_quality_report()` 方法有一个重要的职责：修正 AI 生成的 `shot_id`。

例如storyboard 真正的 ID 是：

```py
known_ids = [
    "shot_01",
    "shot_02",
    "shot_03",
]
```

但是 AI 输出：

```py
shot_id="shot_3"
```

因此 `normalize_quality_report()` 方法会：

1. 校正模型报告中的镜头 ID
2. 转换成经过校验的正式质量报告（StoryboardQualityReport）

 

## 3. 总结

整个设计实际上是：

```
          AI 负责
            ↓
    ModelQualityReport
   ┌────────────────────┐
   │ summary            │
   │ issues             │
   └────────────────────┘
            ↓
   normalize_quality_report()
            ↓
    ① 修正 shot_id
    ② 计算 is_passed
    ③ 计算 affected_shot_ids
    ④ 做一致性校验
            ↓
  StoryboardQualityReport
   ┌────────────────────┐
   │ is_passed          │
   │ summary            │
   │ issues             │
   │ affected_shot_ids  │
   └────────────────────┘
```



---

-EOF-







