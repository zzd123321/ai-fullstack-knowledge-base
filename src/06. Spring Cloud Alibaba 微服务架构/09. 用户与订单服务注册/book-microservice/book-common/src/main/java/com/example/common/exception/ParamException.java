package com.example.common.exception;


import com.example.common.enums.ResultCode;

public class ParamException extends BusinessException {

    public ParamException(String message) {
        super(ResultCode.PARAM_ERROR.getCode(), message);
    }
}
