package com.whomade.kycarrots.config;

import com.whomade.kycarrots.framework.common.util.file.FilePathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URL;

/**
 * 프로젝트 웹 설정 (동적 개선판)
 * FilePathResolver의 설정을 바탕으로 이미지 정적 리소스 핸들러를 자동으로 등록합니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private FilePathResolver filePathResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 로컬 저장소(N) 모드일 때만 실행
        if (!"Y".equalsIgnoreCase(filePathResolver.getStorage().getType())) {
            
            // 등록된 모든 파일 경로 설정을 순회하며 리소스 핸들러 자동 등록
            filePathResolver.getPaths().forEach((category, config) -> {
                String resourcePath = config.getResourcePath();
                String publicUrl = config.getPublicUrl();

                if (resourcePath != null && !resourcePath.isEmpty() && publicUrl != null) {
                    // public-url에서 도메인을 제외한 경로 패턴 추출 (예: /common/img/product/)
                    String pathPattern = extractPathPattern(publicUrl);
                    
                    if (pathPattern != null) {
                        registry.addResourceHandler(pathPattern + "**")
                                .addResourceLocations(resourcePath);
                    }
                }
            });
        }
    }

    /**
     * URL에서 도메인 이후의 경로 부분만 추출합니다.
     * 예: http://domain.com/common/img/product/ -> /common/img/product/
     */
    private String extractPathPattern(String publicUrl) {
        try {
            if (publicUrl.startsWith("http")) {
                URL url = new URL(publicUrl);
                String path = url.getPath();
                return path.endsWith("/") ? path : path + "/";
            } else {
                // 이미 상대 경로인 경우
                return publicUrl.endsWith("/") ? publicUrl : publicUrl + "/";
            }
        } catch (Exception e) {
            return null;
        }
    }

    @org.springframework.context.annotation.Bean
    public com.whomade.kycarrots.framework.interceptor.BranchInterceptor branchInterceptor() {
        return new com.whomade.kycarrots.framework.interceptor.BranchInterceptor();
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(branchInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**", "/mgt/**", "/api/**", "/common/**", "/css/**", "/js/**", "/img/**");
    }
}
