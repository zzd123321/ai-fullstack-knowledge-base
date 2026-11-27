package com.example.common.model;

import com.example.common.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;
    private Long timestamp;

    public Result() {
        timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        timestamp = System.currentTimeMillis();
    }

    //成功的响应结果:无数据
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                null);
    }

    //成功的响应结果:有数据
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getMessage(),
                data);
    }

    //成功的响应结果:自定义消息+数据
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(),
                message,
                data);
    }


    // 失败的响应结果：使用预定义的状态码
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(),
                resultCode.getMessage(),
                null);
    }

    // 失败的响应结果：使用预定义的状态码+自定义消息
    public static <T> Result<T> error(ResultCode resultCode, String message) {
        return new Result<>(resultCode.getCode(),
                message,
                null);
    }

    // 失败的响应结果：自定义状态码+消息
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code,
                message,
                null);
    }
}
