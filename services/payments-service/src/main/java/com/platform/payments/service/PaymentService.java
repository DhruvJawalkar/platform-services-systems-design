package com.platform.payments.service;

import com.platform.payments.config.ServiceProperties;
import com.platform.payments.domain.PaymentResponse;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final ServiceProperties serviceProperties;
    private final Tracer tracer;

    public PaymentService(ServiceProperties serviceProperties, Tracer tracer) {
        this.serviceProperties = serviceProperties;
        this.tracer = tracer;
    }

    public PaymentResponse authorize(String accountId, int amountCents) {
        Span span = tracer.spanBuilder("payments-service.authorize").startSpan();
        span.setAttribute("payments.account_id", accountId);
        span.setAttribute("payments.amount_cents", amountCents);

        try (Scope scope = span.makeCurrent()) {
        logger.info("Authorizing payment for account {} with amount {}", accountId, amountCents);
        return new PaymentResponse(
                UUID.randomUUID().toString(),
                accountId,
                amountCents,
                "AUTHORIZED",
                serviceProperties.getName(),
                serviceProperties.getEnvironment()
        );
        } catch (RuntimeException exception) {
            span.recordException(exception);
            span.setStatus(StatusCode.ERROR);
            throw exception;
        } finally {
            span.end();
        }
    }
}
