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
    private String productReference;
    private String name;
    private double quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
