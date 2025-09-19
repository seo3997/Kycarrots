package com.whomade.kycarrots.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPagesConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/admin/error.do"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/admin/error.do"),
                new ErrorPage(HttpStatus.FORBIDDEN, "/admin/error.do"),
                new ErrorPage(HttpStatus.UNAUTHORIZED, "/admin/login.do") // 세션끊김/미인증 → 로그인
        );
    }
}