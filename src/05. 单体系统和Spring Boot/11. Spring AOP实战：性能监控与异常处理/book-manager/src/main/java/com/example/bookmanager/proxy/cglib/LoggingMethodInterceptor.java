package com.example.bookmanager.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingMethodInterceptor implements MethodInterceptor {


    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 前置增强
        System.out.println("[CGLIB代理] 调用方法：" + method.getName());
        System.out.println("[CGLIB代理代理] 参数：" + Arrays.toString(args));

        // 调用目标方法
        Object result = proxy.invokeSuper(obj, args);

        // 后置增强
        System.out.println("[CGLIB代理] 方法返回：" + result);

        return result;
    }
}
