package com.platform.orders.domain;

public class OrderResponse {

    private final String orderId;
    private final String customerId;
    private final int quantity;
    private final String status;
    private final String serviceName;
    private final String environment;
    private final String paymentId;
    private final String paymentStatus;

    public OrderResponse(String orderId,
                         String customerId,
                         int quantity,
                         String status,
                         String serviceName,
                         String environment,
                         String paymentId,
                         String paymentStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.status = status;
        this.serviceName = serviceName;
        this.environment = environment;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
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

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
