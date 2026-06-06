package com.yjt.basketball.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final RequestLogInterceptor requestLogInterceptor;

    public WebMvcConfig(AuthInterceptor authInterceptor,
                        RequestLogInterceptor requestLogInterceptor) {
        this.authInterceptor = authInterceptor;
        this.requestLogInterceptor = requestLogInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求日志拦截器 —— 最先执行，记录所有请求
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico");
        // 权限拦截器
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
    }
}
