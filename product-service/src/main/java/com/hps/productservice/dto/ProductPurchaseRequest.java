package com.hps.productservice.dto;

public record ProductPurchaseRequest(
        Long productId,
        double quantity 
) {
}
