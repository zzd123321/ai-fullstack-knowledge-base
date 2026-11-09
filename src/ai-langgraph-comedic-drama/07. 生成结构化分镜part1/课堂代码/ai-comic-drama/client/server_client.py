from langgraph_sdk import get_sync_client

SERVER_URL = "http://127.0.0.1:2024"
GRAPH_ID = "comic_drama"
IDEA = "深夜的办公室里，程序员小林正在修改一款漫画应用。零点到来时，他设计的漫画角色阿沐突然从屏幕中走了出来。"


def main() -> None:
    client = get_sync_client(url=SERVER_URL)

    assistants = client.assistants.search(graph_id=GRAPH_ID)
    if not assistants:
        raise RuntimeError(f"Agent Server 中没有找到 Graph：{GRAPH_ID}")

    # search 返回列表；只用第一项，即该图对应的默认 Assistant。
    assistant = assistants[0]

    # 创建一个线程
    thread = client.threads.create(metadata={"title": "零点之后"})

    # 在线程上去跑一轮
    # create() 只负责提交任务并立刻返回 run_id，不会在这里阻塞到图跑完。
    run = client.runs.create(
        thread["thread_id"],
        assistant["assistant_id"],
        input={"raw_requirement": IDEA, "current_stage": "submitted", "events": []},
    )

    # 得到图的执行结果
    # 它不会再创建第二次 Run，只是阻塞直到 create() 那次跑完并返回最终 State。
    result = client.runs.join(thread["thread_id"], run["run_id"])

    # 从线程去拿最新的状态
    state = client.threads.get_state(thread["thread_id"])

    print(f"Graph ID：{assistant['graph_id']}")
    print(f"Assistant ID：{assistant['assistant_id']}")
    print(f"Thread ID：{thread['thread_id']}")
    print(f"Run ID：{run['run_id']}")

    print(f"Run 返回阶段：{result['current_stage']}")
    print(f"Thread 保存阶段：{state['values']['current_stage']}")


if __name__ == "__main__":
    main()
