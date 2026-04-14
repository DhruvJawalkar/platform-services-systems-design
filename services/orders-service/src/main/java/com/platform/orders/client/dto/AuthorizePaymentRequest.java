package com.platform.orders.client.dto;

public class AuthorizePaymentRequest {

    private String accountId;
    private int amountCents;

    public AuthorizePaymentRequest() {
    }

    public AuthorizePaymentRequest(String accountId, int amountCents) {
        this.accountId = accountId;
        this.amountCents = amountCents;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(int amountCents) {
        this.amountCents = amountCents;
    }
}
