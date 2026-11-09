# 该文件用于生成指纹

import hashlib
import json

from server.schema import StoryBoard


def shot_fingerprint(shots: list[StoryBoard]) -> str:
    # 将分镜对象（StoryBoard） -> JSON 对象
    # payload：
    # [
    #   {"shot_id": "shot_01", "order": 1, "action": "小林看到屏幕旁出现阿沐", ...},
    #   {"shot_id": "shot_02", "order": 2, ...},
    #   ...
    # ]
    payload = [shot.model_dump(mode="json") for shot in shots]

    # ensure_ascii=False：中文保持「小林」而不是 \u5c0f\u6797，避免同一内容两种编码。
    # sort_keys=True：dict 键按字母排序
    # encode("utf-8")：把字符串变成哈希函数要的 bytes。
    # raw 开头类似 b'[{"action":"小林看到屏幕旁出现阿沐","order":1,"shot_id":"shot_01",...}]'
    raw = json.dumps(payload, ensure_ascii=False, sort_keys=True).encode("utf-8")

    # 做hash编码，取前面16位
    return hashlib.sha256(raw).hexdigest()[:16]
