package com.hps.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {
    @NotNull
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phone;
    private String factureReference;
    private String code;
    private BigDecimal totalAmount;
}