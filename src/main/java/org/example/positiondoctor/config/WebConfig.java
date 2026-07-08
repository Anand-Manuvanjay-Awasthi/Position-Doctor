package org.example.positiondoctor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration for local development.
 *
 * The project does not use Spring Security, so CORS is configured
 * through {@link WebMvcConfigurer}. This allows the React frontend
 * running on localhost to call the backend without CORS errors.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Allow all origins temporarily for development.
                // allowedOriginPatterns is used (instead of allowedOrigins("*"))
                // so it stays compatible even if credentials are enabled later.
                .allowedOriginPatterns("*")
                // Allow all HTTP methods.
                .allowedMethods("*")
                // Allow all headers.
                .allowedHeaders("*")
                // Credentials are not required by the frontend (axios without withCredentials).
                // Set to true only if cookie/session-based auth is added.
                .allowCredentials(false);
    }
}