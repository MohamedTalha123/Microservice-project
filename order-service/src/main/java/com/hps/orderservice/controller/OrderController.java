package com.hps.orderservice.controller;

import com.hps.orderservice.dto.OrderRequest;
import com.hps.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders ")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

@PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody OrderRequest request){
    return ResponseEntity.ok(orderService.createOrder(request));
}
    
}
