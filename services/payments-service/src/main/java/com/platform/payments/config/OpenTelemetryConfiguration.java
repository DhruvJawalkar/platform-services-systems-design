package com.platform.payments.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfiguration {

    @Bean
    public OpenTelemetry openTelemetry(ServiceProperties serviceProperties, TelemetryProperties telemetryProperties) {
        Resource resource = Resource.getDefault().merge(
                Resource.builder()
                        .put(AttributeKey.stringKey("service.name"), serviceProperties.getName())
                        .put(AttributeKey.stringKey("deployment.environment"), serviceProperties.getEnvironment())
                        .build()
        );

        OtlpGrpcSpanExporter spanExporter = OtlpGrpcSpanExporter.builder()
                .setEndpoint(telemetryProperties.getOtlpEndpoint())
                .build();

        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .setResource(resource)
                .setSampler(Sampler.alwaysOn())
                .addSpanProcessor(BatchSpanProcessor.builder(spanExporter).build())
                .build();

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .build();
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry, ServiceProperties serviceProperties) {
        return openTelemetry.getTracer(serviceProperties.getName());
    }
}
