/*
 * Copyright (c) 2024. Property of EPIC Lanka Technologies Pvt. Ltd.
 */
package com.ecdms.ecdms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for CORS
 * CORS is diabled and all origins are allowed
 *
 * @author Uditha Dayan
 * @version 1.0.0
 * @todo Set up allowed origins for advanced security and better protection against fraudulent requests
 * @since V2.0.0
 */
@Configuration
@Primary
public class WebMvcConfiguration implements WebMvcConfigurer {


    /**
     * CORS configuration method
     * @param registry CORS Registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
       /* registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
        registry.addMapping("/**").allowedMethods("*").allowCredentials(false) */
        registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*");
    }



//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
