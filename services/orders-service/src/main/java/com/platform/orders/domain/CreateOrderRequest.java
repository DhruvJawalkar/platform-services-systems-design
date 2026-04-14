package com.platform.orders.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateOrderRequest {

    @NotBlank
    private String customerId;

    @Min(1)
    private int quantity;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
