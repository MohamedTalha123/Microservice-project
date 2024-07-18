package com.hps.productservice.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        double quantity,
        String imageUrl


) {
}
