package com.example.bookmanager.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 前置增强
        System.out.println("[JDK代理] 调用方法：" + method.getName());
        System.out.println("[JDK代理] 参数：" + Arrays.toString(args));

        // 调用目标方法
        Object result = method.invoke(target, args);

        // 后置增强
        System.out.println("[JDK代理] 方法返回：" + result);

        return result;
    }
}
