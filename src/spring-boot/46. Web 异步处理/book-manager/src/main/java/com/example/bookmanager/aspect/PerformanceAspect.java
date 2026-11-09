package com.example.bookmanager.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    // 慢接口阈值：1000ms
    private static final long SLOW_THRESHOLD = 1000;

    @Pointcut("execution(* com.example.bookmanager.controller..*.*(..))")
    public void controllerMethod() {}

    @Pointcut("execution(* com.example.bookmanager.service..*.*(..))")
    public void serviceMethod() {}

    @Around("controllerMethod() || serviceMethod()")
    public Object measurePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();

        // 2. 记录开始时间
        long startTime = System.currentTimeMillis();

        // 3. 执行目标方法
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            // 记录执行时间（即使发生异常）
            long duration = System.currentTimeMillis() - startTime;
            logger.warn("⏱️ [异常结束] {}.{}() 耗时: {}ms", className, methodName, duration);
            throw throwable;
        }

        // 4. 计算耗时
        long duration = System.currentTimeMillis() - startTime;

        // 5. 判断是否慢接口
        if (duration > SLOW_THRESHOLD) {
            logger.warn("🐢 [慢接口] {}.{}() 耗时: {}ms (超过阈值 {}ms)",
                    className, methodName, duration, SLOW_THRESHOLD);
        } else {
            logger.debug("⚡ [正常] {}.{}() 耗时: {}ms", className, methodName, duration);
        }

        return result;
    }
}