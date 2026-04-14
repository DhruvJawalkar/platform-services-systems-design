package com.platform.payments.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServiceProperties.class, TelemetryProperties.class})
public class ServiceConfiguration {
}
