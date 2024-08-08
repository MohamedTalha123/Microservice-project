package com.hps.orderservice.kafka;

import com.hps.orderservice.dto.PurchaseResponse;
import com.hps.orderservice.dto.UserResponse;
import com.hps.orderservice.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class OrderConfirmation {
    private String orderReference;
    BigDecimal totalAmount;
    PaymentMethod paymentMethod;
    UserResponse userResponse;
    List<PurchaseResponse> products;

}
