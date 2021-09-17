package com.sirma.task.employees.app.config;

import com.sirma.task.employees.app.web.config.WebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Application configuration
 */
@Configuration
@Import({WebConfiguration.class})
public class ApplicationConfiguration {
}
