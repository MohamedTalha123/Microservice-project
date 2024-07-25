package com.hps.orderservice.service;

import com.hps.orderservice.dto.OrderLineRequest;
import com.hps.orderservice.dto.OrderRequest;
import com.hps.orderservice.dto.PurchaseRequest;
import com.hps.orderservice.exception.OrderException;
import com.hps.orderservice.http.ProductClient;
import com.hps.orderservice.http.UserClient;
import com.hps.orderservice.mapper.OrderMapper;
import com.hps.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    public Long createOrder(OrderRequest request) {
        var user = this.userClient.findUserById(request.user_id())
                .orElseThrow(()-> new OrderException("Cannot create order :: No user exist with the provided ID"));

        this.productClient.purchaseProducts(request.products());

        var order = this.orderRepository.save(orderMapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            OrderLineRequest orderLineRequest = new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.product_id(),
                    purchaseRequest.quantity()
            );
            orderLineService.saveOrderLine(orderLineRequest);
        }
        // TODO: payment process
        //TODO: send the order confirmation through notification-service

        return null ;
    }
}
