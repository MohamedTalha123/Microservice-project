package com.hps.productservice.dto;

import com.hps.productservice.entity.Sexe;

import java.math.BigDecimal;

public record ProductRequest(
         Long id,
         String description,
         String name,
         double availableQuantity,
         BigDecimal price,
         String imageUrl,
         Sexe sexe,
         Long categoryId
) {
}
