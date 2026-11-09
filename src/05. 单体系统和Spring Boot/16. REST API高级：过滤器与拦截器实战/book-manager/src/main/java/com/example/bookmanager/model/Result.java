package com.example.bookmanager.model;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; //编码：业务状态码
    private String message; //消息：面向业务场景的具有业务语义的描述信息
    private T data; // 泛型数据
    private Long timestamp; // 响应时间戳
    private String path; // 请求路径

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    // 成功响应（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    // 成功响应（带数据，自定义消息）
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    // 成功响应（无数据）
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }


    // 失败响应（使用预定义的状态码）
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    // 失败响应（使用自定义消息）
    public static <T> Result<T> error(ResultCode resultCode, String message) {
        return new Result<>(resultCode.getCode(), message, null);
    }

    // 失败响应（使用自定义的状态码和消息）
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

}
