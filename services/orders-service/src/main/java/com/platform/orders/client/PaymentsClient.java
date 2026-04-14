package com.platform.orders.client;

import com.platform.orders.client.dto.AuthorizePaymentRequest;
import com.platform.orders.client.dto.PaymentAuthorizationResponse;
import com.platform.orders.config.PaymentsClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentsClient {

    private static final Logger logger = LoggerFactory.getLogger(PaymentsClient.class);

    private final RestTemplate restTemplate;
    private final PaymentsClientProperties paymentsClientProperties;

    public PaymentsClient(RestTemplate restTemplate, PaymentsClientProperties paymentsClientProperties) {
        this.restTemplate = restTemplate;
        this.paymentsClientProperties = paymentsClientProperties;
    }

    public PaymentAuthorizationResponse authorize(String accountId, int amountCents) {
        logger.info("Calling payments-service to authorize {} cents for account {}", amountCents, accountId);

        return restTemplate.postForObject(
                paymentsClientProperties.getBaseUrl() + "/api/v1/payments/authorize",
                new AuthorizePaymentRequest(accountId, amountCents),
                PaymentAuthorizationResponse.class
        );
    }
}
