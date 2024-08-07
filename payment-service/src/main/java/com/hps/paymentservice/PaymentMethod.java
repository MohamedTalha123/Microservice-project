package com.hps.paymentservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    PAYPAL("Paypal"),
    STRIPE("Stripe");

    private final String PaymentMethod;

}

