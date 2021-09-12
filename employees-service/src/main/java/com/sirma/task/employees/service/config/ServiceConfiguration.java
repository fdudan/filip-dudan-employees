package com.sirma.task.employees.service.config;

import com.sirma.task.employees.service.ServiceComponents;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Service configuration.
 */
@Configuration
@ComponentScan(basePackageClasses = ServiceComponents.class)
public class ServiceConfiguration {
}
