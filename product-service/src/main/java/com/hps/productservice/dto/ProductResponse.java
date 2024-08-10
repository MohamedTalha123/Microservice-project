package com.hps.productservice.dto;

import com.hps.productservice.entity.Sexe;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ProductResponse(
        Long id,
        String description,
        String reference,
        String name,
        double availableQuantity,
        BigDecimal price,
        String imageUrl,
        Sexe sexe,
        Long brandId,
        String brandName,
        String brandDescription
) {
}
