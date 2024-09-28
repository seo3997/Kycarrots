package com.whomade.kycarrots.config;

import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author: ADMIN
 * @version: 1.0.0
 * @since: 2024-06-23
 */
@Configuration
@ComponentScan(basePackages = "com.whomade.kycarrots.service")
public class AppConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public EgovMessageSource egovMessageSource(ReloadableResourceBundleMessageSource messageSource) {
        return new EgovMessageSource(messageSource);
    }
}
