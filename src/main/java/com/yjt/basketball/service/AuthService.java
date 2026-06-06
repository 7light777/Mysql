package com.yjt.basketball.service;

import com.yjt.basketball.dto.AuthUser;
import com.yjt.basketball.dto.row.AuthUserRow;
import com.yjt.basketball.mapper.AuthMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public static final String SESSION_USER = "LOGIN_USER";

    private final AuthMapper authMapper;

    public AuthService(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @PostConstruct
    public void initializeUsers() {
        authMapper.createUserAccountTable();
        authMapper.insertDefaultUsers();
    }

    public AuthUser login(String username, String password) {
        log.info("===== 用户登录 =====");
        log.info("登录用户名：{}", username);
        AuthUserRow row = authMapper.selectByUsernameAndPassword(username, password);
        if (row == null) {
            log.warn("登录失败：用户名或密码错误，username={}", username);
            throw new RuntimeException("用户名或密码错误");
        }
        log.info("登录成功：username={}, displayName={}, role={}",
                row.getUsername(), row.getDisplayName(), row.getRole());
        return new AuthUser(
                row.getUserId(),
                row.getUsername(),
                row.getDisplayName(),
                row.getRole()
        );
    }

    public AuthUser currentUser(HttpSession session) {
        Object user = session.getAttribute(SESSION_USER);
        return user instanceof AuthUser authUser ? authUser : null;
    }
}
