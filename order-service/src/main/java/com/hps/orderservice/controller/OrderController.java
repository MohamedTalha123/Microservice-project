package com.hps.orderservice.controller;

import com.hps.orderservice.dto.OrderRequest;
import com.hps.orderservice.entity.Order;
import com.hps.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

@PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request){
    return ResponseEntity.ok(orderService.createOrder(request));
}
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @PutMapping
    public ResponseEntity<Order> updateOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
}
