package com.hps.paymentservice.dto;

import lombok.Builder;

@Builder

public record PurchaseRequest(
        Long product_id,
        double quantity
) {
}
