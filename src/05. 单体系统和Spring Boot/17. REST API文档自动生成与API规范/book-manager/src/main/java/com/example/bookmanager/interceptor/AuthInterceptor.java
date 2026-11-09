package com.example.bookmanager.interceptor;

import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取来自请求头中的Token
        String token = request.getHeader("Authorization");

        // 模拟权限验证：检查Token是否才有特定的标识(Bearer)
        if (token == null || !token.startsWith("Bearer ")) {
            logger.warn("未认证请求：{}", request.getRequestURI());

            // 返回 401 未认证的HTTP状态码
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=utf-8");

            Result<?> result = Result.error(ResultCode.UNAUTHORIZED);

            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();

            return false; // 拦截请求，当前请求不在继续执行
        }

        // 模拟从Token中解析用户Id
        String userId = token.substring(7); // 去掉“Bearer ”这部分字符串
        request.setAttribute("userId", userId);
        logger.info("认证通过，用户ID：{}", userId);

        return true; // 该方法会继续执行
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if(ex != null) {
            logger.error("请求异常：{}", request.getRequestURI(), ex);
        }
    }
}
