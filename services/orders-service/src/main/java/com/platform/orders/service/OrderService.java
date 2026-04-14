package com.platform.orders.service;

import com.platform.orders.client.PaymentsClient;
import com.platform.orders.client.dto.PaymentAuthorizationResponse;
import com.platform.orders.config.ServiceProperties;
import com.platform.orders.domain.OrderResponse;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final ServiceProperties serviceProperties;
    private final PaymentsClient paymentsClient;
    private final Tracer tracer;

    public OrderService(ServiceProperties serviceProperties, PaymentsClient paymentsClient, Tracer tracer) {
        this.serviceProperties = serviceProperties;
        this.paymentsClient = paymentsClient;
        this.tracer = tracer;
    }

    public OrderResponse sampleOrder(String customerId, int quantity) {
        return new OrderResponse(
                UUID.randomUUID().toString(),
                customerId,
                quantity,
                "ACCEPTED",
                serviceProperties.getName(),
                serviceProperties.getEnvironment(),
                "not-requested",
                "SKIPPED"
        );
    }

    public OrderResponse createOrder(String customerId, int quantity) {
        Span span = tracer.spanBuilder("orders-service.create-order").startSpan();
        span.setAttribute("orders.customer_id", customerId);
        span.setAttribute("orders.quantity", quantity);

        try (Scope scope = span.makeCurrent()) {
        int amountCents = quantity * 1000;
        logger.info("Creating order for customer {} with quantity {}", customerId, quantity);

        PaymentAuthorizationResponse payment = paymentsClient.authorize(customerId, amountCents);

        return new OrderResponse(
                UUID.randomUUID().toString(),
                customerId,
                quantity,
                "ACCEPTED",
                serviceProperties.getName(),
                serviceProperties.getEnvironment(),
                payment.getPaymentId(),
                payment.getStatus()
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
