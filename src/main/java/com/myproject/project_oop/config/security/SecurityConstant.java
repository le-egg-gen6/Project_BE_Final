package com.myproject.project_oop.config.security;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SecurityConstant {

    private final String[] whiteListUrl = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

}
