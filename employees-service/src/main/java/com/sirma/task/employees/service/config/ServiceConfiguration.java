package com.sirma.task.employees.service.config;

import com.sirma.task.employees.service.ServiceComponents;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Service configuration.
 */
@Configuration
@ComponentScan(basePackageClasses = ServiceComponents.class)
public class ServiceConfiguration {
}
