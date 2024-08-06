package com.hps.orderservice.dto;

import com.hps.orderservice.dto.PurchaseRequest;
import com.hps.orderservice.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(

        Long product_id,
        double quantity,
        BigDecimal amount,
        Long user_id
) {
}
