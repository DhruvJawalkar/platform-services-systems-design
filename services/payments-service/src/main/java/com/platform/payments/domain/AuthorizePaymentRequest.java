package com.platform.payments.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AuthorizePaymentRequest {

    @NotBlank
    private String accountId;

    @Min(1)
    private int amountCents;

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
