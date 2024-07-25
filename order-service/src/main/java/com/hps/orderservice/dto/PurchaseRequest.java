package com.hps.orderservice.dto;

public record PurchaseRequest(
        Long product_id,
        double quantity
) {
}
