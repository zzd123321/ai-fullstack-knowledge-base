package com.example.bookmanager.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @Pointcut("execution(* com.example.bookmanager.controller..*.*(..))")
    public void controllerMethod() {}

    @AfterThrowing(pointcut = "controllerMethod()", throwing = "exception")
    public void handleException(Exception exception) {
        // 记录异常日志
        logger.error("统一异常处理: {}", exception.getMessage(), exception);
        // 注意：这里不能直接修改返回值，实际返回值的统一处理需要配合 @ControllerAdvice
        // 本切面仅用于异常日志记录，业务异常的返回值由 @ControllerAdvice 统一处理
    }
}