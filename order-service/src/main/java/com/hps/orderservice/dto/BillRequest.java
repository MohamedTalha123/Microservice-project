package com.hps.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder

public class BillRequest {
    private String phone;

    private Long clientId;

    private Long orderId;

    private BigDecimal amount;
}
