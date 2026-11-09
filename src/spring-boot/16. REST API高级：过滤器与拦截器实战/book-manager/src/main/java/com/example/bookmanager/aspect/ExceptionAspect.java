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
        logger.error("统一异常处理：{}", exception.getMessage(), exception);

        // 注意：这种做法只能获取异常结果，不能直接修改返回值
        // 后续需要用到@ControllerAdvice来返回统一的异常处理结果
    }
}
