package com.sirma.task.employees.app.config;

import com.sirma.task.employees.web.config.WebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WebConfiguration.class)
public class ApplicationConfiguration {
}
