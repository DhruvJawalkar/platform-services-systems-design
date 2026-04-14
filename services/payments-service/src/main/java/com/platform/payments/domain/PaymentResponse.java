package com.platform.payments.domain;

public class PaymentResponse {

    private final String paymentId;
    private final String accountId;
    private final int amountCents;
    private final String status;
    private final String serviceName;
    private final String environment;

    public PaymentResponse(String paymentId,
                           String accountId,
                           int amountCents,
                           String status,
                           String serviceName,
                           String environment) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.amountCents = amountCents;
        this.status = status;
        this.serviceName = serviceName;
        this.environment = environment;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getAmountCents() {
        return amountCents;
    }

    public String getStatus() {
        return status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getEnvironment() {
        return environment;
    }
}
