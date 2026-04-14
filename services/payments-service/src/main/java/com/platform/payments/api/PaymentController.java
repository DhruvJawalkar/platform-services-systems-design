package com.platform.payments.api;

import com.platform.payments.domain.AuthorizePaymentRequest;
import com.platform.payments.domain.PaymentResponse;
import com.platform.payments.service.PaymentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/health")
    public PaymentResponse health() {
        return paymentService.authorize("health-account", 100);
    }

    @PostMapping("/authorize")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse authorize(@Valid @RequestBody AuthorizePaymentRequest request) {
        return paymentService.authorize(request.getAccountId(), request.getAmountCents());
    }
}
