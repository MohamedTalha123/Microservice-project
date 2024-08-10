package com.hps.orderservice.dto;

import com.hps.orderservice.dto.PurchaseRequest;
import com.hps.orderservice.entity.PaymentMethod;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
public record OrderRequest(

        Long product_id,
        double quantity,
        Long user_id
) {
}
