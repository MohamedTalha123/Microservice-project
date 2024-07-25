package com.hps.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseResponse {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private double quantity;
    private String imageUrl;
}
