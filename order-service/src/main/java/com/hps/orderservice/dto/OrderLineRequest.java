package com.hps.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLineRequest(
         Long id,
         Long orderId,
         Long productId,
         double quantity
) {
}
