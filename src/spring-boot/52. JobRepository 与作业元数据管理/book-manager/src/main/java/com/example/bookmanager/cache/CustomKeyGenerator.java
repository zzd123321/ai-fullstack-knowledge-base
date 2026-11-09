package com.example.bookmanager.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();

        // 1. 添加类名
        key.append(target.getClass().getSimpleName());
        key.append(":");

        // 2. 添加方法名
        key.append(method.getName());
        key.append(":");

        // 3. 添加参数
        if(params.length == 0) {
            key.append("void");
        } else {
            key.append(Arrays.toString(params));
        }

        return key.toString();
    }
}
