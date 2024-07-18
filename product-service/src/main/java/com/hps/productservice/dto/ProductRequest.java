package com.hps.productservice.dto;

import java.math.BigDecimal;

public record ProductRequest(
         Long id,
         String description,
         String name,
         double availableQuantity,
         BigDecimal price,
         String imageUrl,
         Long categoryId
) {
}
