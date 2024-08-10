package com.hps.orderservice.mapper;

import com.hps.orderservice.dto.OrderLineRequest;
import com.hps.orderservice.dto.OrderLineResponse;
import com.hps.orderservice.entity.OrderLineItem;
import com.hps.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderLineMapper {
    private final OrderRepository orderRepository;

    public OrderLineItem toOrderLine(OrderLineRequest request) {
        return OrderLineItem.builder()
                .order(orderRepository.findById(request.orderId()).orElseThrow(() -> new RuntimeException("Order not found")))
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLineItem orderLineItem) {
        return OrderLineResponse.builder()
                .id(orderLineItem.getId())
                .orderId(orderLineItem.getOrder().getId())
                .productReference(orderLineItem.getProductReference())
                .quantity(orderLineItem.getQuantity())
                .totalPrice(orderLineItem.getPrice())
                .unitPrice(orderLineItem.getPrice().divide(BigDecimal.valueOf(orderLineItem.getQuantity()), BigDecimal.ROUND_HALF_UP))
                .build();
    }
}
