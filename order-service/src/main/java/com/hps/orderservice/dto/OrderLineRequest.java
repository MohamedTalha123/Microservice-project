package com.hps.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderLineRequest(
         Long id,
         Long orderId,
         Long productId,
         double quantity
) {
}
