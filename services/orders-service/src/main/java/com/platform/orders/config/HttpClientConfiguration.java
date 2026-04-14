package com.platform.orders.config;

import com.platform.orders.web.CorrelationIdFilter;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors((request, body, execution) -> {
                    addCorrelationHeader(request);
                    return execution.execute(request, body);
                })
                .build();
    }

    private void addCorrelationHeader(HttpRequest request) {
        String correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY);
        if (correlationId != null && !correlationId.isBlank()) {
            request.getHeaders().set(CorrelationIdFilter.CORRELATION_ID_HEADER, correlationId);
        }
    }
}
