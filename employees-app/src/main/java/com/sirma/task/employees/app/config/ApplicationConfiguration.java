package com.sirma.task.employees.app.config;

import com.sirma.task.employees.service.config.ServiceConfiguration;
import com.sirma.task.employees.web.config.WebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Application configuration
 */
@Configuration
@Import({WebConfiguration.class,
    ServiceConfiguration.class})
public class ApplicationConfiguration {
}
