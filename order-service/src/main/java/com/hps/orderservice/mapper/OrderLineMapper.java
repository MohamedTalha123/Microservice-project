package com.hps.orderservice.mapper;

import com.hps.orderservice.dto.OrderLineRequest;
import com.hps.orderservice.entity.Order;
import com.hps.orderservice.entity.OrderLineItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderLineMapper {
    public OrderLineItem toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLineItem.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(Order.builder()
                        .id(orderLineRequest.orderId())
                        .build())
                .productId(orderLineRequest.productId())
                .build();
    }
}
