package com.hps.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String reference;
    private String description;
    private double availableQuantity;
    private BigDecimal price;
    private String imageUrl;
}