package com.yjt.basketball.common;

import com.yjt.basketball.dto.AuthUser;
import com.yjt.basketball.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAdvice {

    private final AuthService authService;

    public GlobalModelAdvice(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("currentUser")
    public AuthUser currentUser(HttpSession session) {
        return authService.currentUser(session);
    }
}
