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
            new ErrorPage(HttpStatus.NOT_FOUND, "/admin/login.do"),          // 404
            new ErrorPage(HttpStatus.UNAUTHORIZED, "/admin/login.do"),       // 401
            new ErrorPage(HttpStatus.FORBIDDEN, "/admin/login.do"),          // 403
            new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/admin/login.do") // 500
        );
    }
}
