package com.hps.orderservice.service;

import com.hps.orderservice.dto.OrderLineRequest;
import com.hps.orderservice.dto.OrderLineResponse;
import com.hps.orderservice.entity.OrderLineItem;
import com.hps.orderservice.exception.OrderException;
import com.hps.orderservice.http.ProductClient;
import com.hps.orderservice.mapper.OrderLineMapper;
import com.hps.orderservice.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    private final ProductClient productClient;

    @Transactional
    public Long saveOrderLine(OrderLineRequest orderLineRequest) {
        var orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();
    }

    public OrderLineResponse getOrderLineById(Long id) {
        OrderLineItem orderLineItem = orderLineRepository.findById(id)
                .orElseThrow(() -> new OrderException("OrderLineItem not found"));
        return orderLineMapper.toOrderLineResponse(orderLineItem);
    }

    public List<OrderLineResponse> getAllOrderLines() {
        return orderLineRepository.findAll().stream()
                .map(orderLineMapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderLineResponse updateOrderLine(Long id, OrderLineRequest orderLineRequest) {
        OrderLineItem orderLineItem = orderLineRepository.findById(id)
                .orElseThrow(() -> new OrderException("OrderLineItem not found"));

        var product = productClient.getProductById(orderLineRequest.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(orderLineRequest.quantity()));

        orderLineItem.setProductId(orderLineRequest.productId());
        orderLineItem.setQuantity(orderLineRequest.quantity());
        orderLineItem.setPrice(totalPrice);
        return orderLineMapper.toOrderLineResponse(orderLineRepository.save(orderLineItem));
    }


    @Transactional
    public void deleteOrderLine(Long id) {
        OrderLineItem orderLineItem = orderLineRepository.findById(id)
                .orElseThrow(() -> new OrderException("OrderLineItem not found"));
        orderLineRepository.delete(orderLineItem);
    }
}
