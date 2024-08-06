package com.hps.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder

public record PurchaseRequest(
        Long product_id,
        double quantity
) {
}
