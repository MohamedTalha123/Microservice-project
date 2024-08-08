package com.hps.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineRequest(
         Long orderId,
         Long productId,
         double quantity
) {
}
