package com.platform.orders.client;

import com.platform.orders.client.dto.AuthorizePaymentRequest;
import com.platform.orders.client.dto.PaymentAuthorizationResponse;
import com.platform.orders.config.PaymentsClientProperties;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentsClient {

    private static final Logger logger = LoggerFactory.getLogger(PaymentsClient.class);

    private final RestTemplate restTemplate;
    private final PaymentsClientProperties paymentsClientProperties;
    private final Tracer tracer;

    public PaymentsClient(RestTemplate restTemplate,
                          PaymentsClientProperties paymentsClientProperties,
                          Tracer tracer) {
        this.restTemplate = restTemplate;
        this.paymentsClientProperties = paymentsClientProperties;
        this.tracer = tracer;
    }

    public PaymentAuthorizationResponse authorize(String accountId, int amountCents) {
        logger.info("Calling payments-service to authorize {} cents for account {}", amountCents, accountId);

        Span span = tracer.spanBuilder("payments-service.authorize")
                .setSpanKind(SpanKind.CLIENT)
                .startSpan();
        span.setAttribute("payments.account_id", accountId);
        span.setAttribute("payments.amount_cents", amountCents);

        try (Scope scope = span.makeCurrent()) {
            PaymentAuthorizationResponse response = restTemplate.postForObject(
                    paymentsClientProperties.getBaseUrl() + "/api/v1/payments/authorize",
                    new AuthorizePaymentRequest(accountId, amountCents),
                    PaymentAuthorizationResponse.class
            );

            if (response != null) {
                span.setAttribute("payments.status", response.getStatus());
            }
            return response;
        } catch (RuntimeException exception) {
            span.recordException(exception);
            span.setStatus(StatusCode.ERROR);
            throw exception;
        } finally {
            span.end();
        }
    }
}
