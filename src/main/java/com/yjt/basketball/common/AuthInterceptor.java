package com.yjt.basketball.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yjt.basketball.dto.AuthUser;
import com.yjt.basketball.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    public AuthInterceptor(AuthService authService, ObjectMapper objectMapper) {
        this.authService = authService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (isPublicPath(path)) {
            return true;
        }

        AuthUser user = authService.currentUser(request.getSession());
        if (user == null) {
            if (isApi(path)) {
                writeApiError(response, HttpServletResponse.SC_UNAUTHORIZED, "请先登录");
            } else {
                response.sendRedirect("/login");
            }
            return false;
        }

        if (!hasPermission(user, request.getMethod(), path)) {
            if (isApi(path)) {
                writeApiError(response, HttpServletResponse.SC_FORBIDDEN, "权限不足");
            } else {
                response.sendRedirect("/");
            }
            return false;
        }
        return true;
    }

    private boolean isPublicPath(String path) {
        return path.equals("/login")
                || path.equals("/error")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/favicon");
    }

    private boolean hasPermission(AuthUser user, String method, String path) {
        if (user.isAdmin()) {
            return true;
        }
        if ("DELETE".equals(method) && path.startsWith("/api/games/")) {
            return false;
        }
        if (path.equals("/stats-update") || path.equals("/api/stats/current")
                || ("PUT".equals(method) && path.equals("/api/stats"))) {
            return false;
        }
        if (path.equals("/delete-game")) {
            return false;
        }
        if ("VIEWER".equals(user.role())) {
            // 访客可查看球探报告页面和读取数据，但不能增删改
            if (path.startsWith("/api/scout-notes") && !"GET".equals(method)) {
                return false;
            }
        }
        return true;
    }

    private boolean isApi(String path) {
        return path.startsWith("/api/");
    }

    private void writeApiError(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(message)));
    }
}
