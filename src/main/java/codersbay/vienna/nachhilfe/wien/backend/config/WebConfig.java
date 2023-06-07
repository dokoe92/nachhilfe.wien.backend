package codersbay.vienna.nachhilfe.wien.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * Allows requests from any origin, with any method and headers.
     *
     * MUST BE FIXED!!! ORIGINS MUST BE CAREFULLY DEFINED DUE TO SAFETY REASONS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}