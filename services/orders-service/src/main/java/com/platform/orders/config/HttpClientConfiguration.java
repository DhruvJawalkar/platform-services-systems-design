package com.platform.orders.config;

import com.platform.orders.web.CorrelationIdFilter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Context;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, OpenTelemetry openTelemetry) {
        return builder
                .additionalInterceptors((request, body, execution) -> {
                    addCorrelationHeader(request);
                    openTelemetry.getPropagators()
                            .getTextMapPropagator()
                            .inject(Context.current(), request, (carrier, key, value) -> carrier.getHeaders().set(key, value));
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
