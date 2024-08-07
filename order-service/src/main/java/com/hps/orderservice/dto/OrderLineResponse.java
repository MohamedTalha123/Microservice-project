package com.hps.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@Data
@Getter
@Setter
public class OrderLineResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private double quantity;
    private BigDecimal productPrice;
    private BigDecimal price;
}
