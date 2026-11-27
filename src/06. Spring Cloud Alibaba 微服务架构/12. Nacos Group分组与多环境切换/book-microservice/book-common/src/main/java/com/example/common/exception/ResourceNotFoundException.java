package com.example.common.exception;

import com.example.common.enums.ResultCode;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(ResultCode.RESOURCE_NOT_FOUND.getCode(), message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(ResultCode.RESOURCE_NOT_FOUND.getCode(), resource + "不存在，id：" + id);
    }
}
