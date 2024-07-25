package com.hps.orderservice.mapper;

import com.hps.orderservice.dto.OrderRequest;
import com.hps.orderservice.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .id(request.id())
                .userId(request.user_id())
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();

    }
}
