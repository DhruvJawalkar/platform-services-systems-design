package com.platform.payments.web;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TracingFilter extends OncePerRequestFilter {

    private static final TextMapGetter<HttpServletRequest> REQUEST_GETTER = new TextMapGetter<HttpServletRequest>() {
        @Override
        public Iterable<String> keys(HttpServletRequest carrier) {
            Enumeration<String> headerNames = carrier.getHeaderNames();
            return headerNames == null ? Collections.emptyList() : Collections.list(headerNames);
        }

        @Override
        public String get(HttpServletRequest carrier, String key) {
            return carrier.getHeader(key);
        }
    };

    private final OpenTelemetry openTelemetry;
    private final Tracer tracer;

    public TracingFilter(OpenTelemetry openTelemetry, Tracer tracer) {
        this.openTelemetry = openTelemetry;
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Context extractedContext = openTelemetry.getPropagators()
                .getTextMapPropagator()
                .extract(Context.current(), request, REQUEST_GETTER);

        Span span = tracer.spanBuilder(request.getMethod() + " " + request.getRequestURI())
                .setParent(extractedContext)
                .setSpanKind(SpanKind.SERVER)
                .startSpan();

        span.setAttribute("http.method", request.getMethod());
        span.setAttribute("http.target", request.getRequestURI());

        try (Scope scope = span.makeCurrent()) {
            MDC.put("traceId", span.getSpanContext().getTraceId());
            MDC.put("spanId", span.getSpanContext().getSpanId());
            filterChain.doFilter(request, response);
            span.setAttribute("http.status_code", response.getStatus());
            if (response.getStatus() >= 500) {
                span.setStatus(StatusCode.ERROR);
            }
        } catch (Exception exception) {
            span.recordException(exception);
            span.setStatus(StatusCode.ERROR);
            throw exception;
        } finally {
            MDC.remove("traceId");
            MDC.remove("spanId");
            span.end();
        }
    }
}
