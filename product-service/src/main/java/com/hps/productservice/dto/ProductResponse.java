package com.hps.productservice.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String description,
        String name,
        double availableQuantity,
        BigDecimal price,
        String imageUrl,
        Long categoryId,
        String categoryName,
        String categoryDescription
) {
}
