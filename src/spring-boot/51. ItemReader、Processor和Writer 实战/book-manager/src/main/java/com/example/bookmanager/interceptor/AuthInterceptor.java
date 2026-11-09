package com.example.bookmanager.interceptor;

import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    /**
     * 前置处理：在 Controller 方法执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取请求头中的 Token
        String token = request.getHeader("Authorization");

        // 模拟权限校验：检查 Token 是否有效
        if (token == null || !token.startsWith("Bearer ")) {
            logger.warn("未认证请求: {}", request.getRequestURI());
            // 返回 401 未认证
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            Result<?> result = Result.error(ResultCode.UNAUTHORIZED);
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(result));
            writer.flush();
            return false;  // 拦截请求，不再继续执行
        }

        // 模拟从 Token 中解析用户 ID（实际项目可使用 JWT）
        String userId = token.substring(7); // 去掉 "Bearer "
        request.setAttribute("userId", userId);
        logger.info("认证通过，用户ID: {}", userId);

        return true;  // 继续执行
    }

    /**
     * 后置处理：在 Controller 方法执行之后，视图渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里对响应数据进行额外处理
        // 对于 REST API，通常不做特殊处理
    }

    /**
     * 完成处理：在视图渲染之后调用，用于资源清理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        if (ex != null) {
            logger.error("请求异常: {}", request.getRequestURI(), ex);
        }
    }
}