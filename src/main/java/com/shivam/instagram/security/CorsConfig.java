package com.shivam.instagram.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
public class CorsConfig implements CorsConfigurationSource
{

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        
        CorsConfiguration cors = new CorsConfiguration();
            
        cors
            .setAllowedOriginPatterns(List.of("http://127.0.0.1:5500/"));

        cors
            .setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        cors
            .setAllowCredentials(true);

        cors
            .setAllowedHeaders(List.of("*"));


        return cors;
                
    }
    
}
