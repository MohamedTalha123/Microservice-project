package com.hps.orderservice.dto;

import com.hps.orderservice.dto.PurchaseRequest;
import com.hps.orderservice.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Long id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long user_id,
        List<PurchaseRequest> products
) {
}
