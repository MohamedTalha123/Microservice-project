package com.hps.orderservice.dto;

public record OrderLineRequest(
         Long id,
         Long orderId,
         Long productId,
         double quantity
) {
}
