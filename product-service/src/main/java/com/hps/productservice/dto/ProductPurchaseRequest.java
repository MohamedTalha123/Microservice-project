package com.hps.productservice.dto;

public record ProductPurchaseRequest(
        Long product_id,
        double quantity 
) {
}
