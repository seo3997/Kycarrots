package com.whomade.kycarrots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableTransactionManagement
@ConfigurationPropertiesScan
public class KycarrotsApplication{

	public static void main(String[] args) {
		SpringApplication.run(KycarrotsApplication.class, args);
	}

}
