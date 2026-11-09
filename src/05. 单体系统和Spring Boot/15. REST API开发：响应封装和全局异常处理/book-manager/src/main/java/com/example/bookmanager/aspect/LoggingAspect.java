package com.example.bookmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // 创建Logger
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 定义切点：匹配 com.example.bookmanager.controller 包及其子包下的所有方法
     */
    @Pointcut("execution(* com.example.bookmanager.controller..*.*(..))")
    public void controllerMethod() {}

    /**
     * 前置通知：在目标方法执行前执行
     */
    @Before("controllerMethod()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("🔵 调用方法: {}，参数: {}", methodName, args);
    }

    /**
     * 后置通知：在目标方法正常返回后执行
     */
    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("🟢 方法返回: {}，结果: {}", methodName, result);
    }

    /**
     * 异常通知：在目标方法抛出异常后执行
     */
    @AfterThrowing(pointcut = "controllerMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("🔴 方法异常: {}，异常信息: {}", methodName, exception.getMessage());
    }

    /**
     * 最终通知：在目标方法执行完成后执行（无论是否异常）
     */
    @After("controllerMethod()")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("⬜ 方法执行完成: {}", methodName);
    }
}