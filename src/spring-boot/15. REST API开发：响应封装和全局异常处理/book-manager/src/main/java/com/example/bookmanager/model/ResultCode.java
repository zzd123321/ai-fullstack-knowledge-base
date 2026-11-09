package com.example.bookmanager.model;

public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),
    CREATED(201, "创建成功"),
    NO_CONTENT(204, "操作成功，无返回数据"),

    // 客户端错误 400-499
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未认证或登录已过期"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    PARAM_ERROR(422, "参数校验失败"),

    // 服务器错误 500-599
    INTERNAL_ERROR(500, "系统内部错误，请稍后重试"),
    SERVICE_UNAVAILABLE(503, "服务暂时不可用"),

    // 业务错误 1000-1999
    BUSINESS_ERROR(1000, "业务处理失败"),
    RESOURCE_NOT_FOUND(1001, "资源不存在"),
    RESOURCE_EXISTS(1002, "资源已存在"),
    INVALID_OPERATION(1003, "操作不允许"),
    STOCK_INSUFFICIENT(1004, "库存不足");

    // TODO：根据业务场景不断添加新的业务状态码

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {return code;}
    public String getMessage() {return message;}
}
