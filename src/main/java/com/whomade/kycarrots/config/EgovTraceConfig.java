package com.whomade.kycarrots.config;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EgovTraceConfig {

    @Bean(name = "leaveaTrace")
    public LeaveaTrace leaveaTrace() {
        return new LeaveaTrace(); // 기본 생성자 사용
    }
}
