package com.platform.payments.service;

import com.platform.payments.config.ServiceProperties;
import com.platform.payments.domain.PaymentResponse;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final ServiceProperties serviceProperties;

    public PaymentService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public PaymentResponse authorize(String accountId, int amountCents) {
        logger.info("Authorizing payment for account {} with amount {}", accountId, amountCents);
        return new PaymentResponse(
                UUID.randomUUID().toString(),
                accountId,
                amountCents,
                "AUTHORIZED",
                serviceProperties.getName(),
                serviceProperties.getEnvironment()
        );
    }
}
