package com.platform.payments.service;

import com.platform.payments.config.ServiceProperties;
import com.platform.payments.domain.PaymentResponse;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final ServiceProperties serviceProperties;

    public PaymentService(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public PaymentResponse authorize(String accountId, int amountCents) {
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
