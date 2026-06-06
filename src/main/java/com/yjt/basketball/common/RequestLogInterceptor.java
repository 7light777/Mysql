package com.yjt.basketball.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

/**
 * 请求日志拦截器 —— 参考苍穹外卖风格
 * 功能：在控制台清晰打印每次请求的路径、参数、耗时，方便答辩演示
 */
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME, System.currentTimeMillis());

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        log.info("╔═══════════════════ 接口请求开始 ═══════════════════");
        log.info("║ 请求方式：{}", method);
        log.info("║ 请求地址：{}", uri);
        if (queryString != null && !queryString.isEmpty()) {
            log.info("║ 请求参数：{}", queryString);
        }
        log.info("╚════════════════════════════════════════════════════");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;

        int status = response.getStatus();
        if (ex != null) {
            log.error("╔═══════════════════ 接口请求异常 ═══════════════════");
            log.error("║ 请求地址：{} {}", request.getMethod(), request.getRequestURI());
            log.error("║ 异常信息：{}", ex.getMessage());
            log.error("║ 耗时：{} ms", duration);
            log.error("╚════════════════════════════════════════════════════");
        } else {
            log.info("╔═══════════════════ 接口请求结束 ═══════════════════");
            log.info("║ 请求地址：{} {}", request.getMethod(), request.getRequestURI());
            log.info("║ 响应状态：{}", status);
            log.info("║ 耗时：{} ms", duration);
            log.info("╚════════════════════════════════════════════════════");
        }
    }
}
