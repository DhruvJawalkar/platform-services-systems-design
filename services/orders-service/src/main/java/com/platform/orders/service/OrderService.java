package com.platform.orders.service;

import com.platform.orders.client.PaymentsClient;
import com.platform.orders.client.dto.PaymentAuthorizationResponse;
import com.platform.orders.config.ServiceProperties;
import com.platform.orders.domain.OrderResponse;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final ServiceProperties serviceProperties;
    private final PaymentsClient paymentsClient;

    public OrderService(ServiceProperties serviceProperties, PaymentsClient paymentsClient) {
        this.serviceProperties = serviceProperties;
        this.paymentsClient = paymentsClient;
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
    }
}
