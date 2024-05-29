package com.myproject.project_oop.config.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SwaggerConstant {

    @Value("${swagger.host.address}")
    private String hostAddress;

    @Value("${swagger.host.path}")
    private String hostPath;

    @Value("${swagger.api.name}")
    private String apiName;

    @Value("${swagger.api.version}")
    private String apiVersion;

    @Value("${swagger.contact.name}")
    private String contactName;

    @Value("${swagger.contact.url}")
    private String contactUrl;

    @Value("${swagger.contact.email}")
    private String contactEmail;

}
