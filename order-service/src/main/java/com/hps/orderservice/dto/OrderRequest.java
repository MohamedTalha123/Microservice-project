package com.hps.orderservice.dto;

public record OrderRequest(

        Long product_id,
        double quantity,
        Long user_id
) {
}
