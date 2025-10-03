package com.whomade.kycarrots.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.product.resource-path}")
    private String resourcePath;

    @Value("${file.board.resource-path}")
    private String boardResourcePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/common/img/product/**")
                .addResourceLocations(resourcePath);
        registry.addResourceHandler("/common/img/board/**")
                .addResourceLocations(boardResourcePath);
    }
}
