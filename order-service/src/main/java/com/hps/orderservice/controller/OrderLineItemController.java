package com.hps.orderservice.controller;

import com.hps.orderservice.dto.OrderLineRequest;
import com.hps.orderservice.dto.OrderLineResponse;
import com.hps.orderservice.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-line-items")
@RequiredArgsConstructor
public class OrderLineItemController {
    private final OrderLineService orderLineService;

    @PostMapping
    public ResponseEntity<Long> createOrderLineItem(@RequestBody OrderLineRequest orderLineRequest) {
        return ResponseEntity.ok(orderLineService.saveOrderLine(orderLineRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLineResponse> getOrderLineItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderLineService.getOrderLineById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderLineResponse>> getAllOrderLineItems() {
        return ResponseEntity.ok(orderLineService.getAllOrderLines());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderLineResponse> updateOrderLineItem(@PathVariable Long id, @RequestBody OrderLineRequest orderLineRequest) {
        return ResponseEntity.ok(orderLineService.updateOrderLine(id, orderLineRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderLineItem(@PathVariable Long id) {
        orderLineService.deleteOrderLine(id);
        return ResponseEntity.noContent().build();
    }
}
