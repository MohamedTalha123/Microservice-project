package com.hps.orderservice.service;

import com.hps.orderservice.dto.OrderLineRequest;
import com.hps.orderservice.mapper.OrderLineMapper;
import com.hps.orderservice.repository.OrderLineRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;


    public  Long saveOrderLine(OrderLineRequest orderLineRequest) {
        var order =  orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(order).getId();
    }


}
