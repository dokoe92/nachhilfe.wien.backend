package codersbay.vienna.nachhilfe.wien.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 Configuration class for the application.
 Enables JPA auditing to automatically populate created and updated timestamps.
 */
@Configuration
@EnableJpaAuditing
public class ApplicationConfig {
}
