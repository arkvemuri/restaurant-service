package com.restaurant.example.restaurant_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * Web configuration for the Restaurant Service.
 * Provides CORS configuration for API Gateway architecture.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:8080,http://localhost:9090}")
    private String allowedOrigins;

    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;

    @Value("${app.cors.allow-credentials:true}")
    private boolean allowCredentials;

    @Value("${app.cors.max-age:3600}")
    private long maxAge;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        List<String> methods = Arrays.asList(allowedMethods.split(","));
        
        registry.addMapping("/restaurant/**")
                .allowedOriginPatterns(origins.toArray(new String[0]))
                .allowedMethods(methods.toArray(new String[0]))
                .allowedHeaders("*")
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);
    }
}