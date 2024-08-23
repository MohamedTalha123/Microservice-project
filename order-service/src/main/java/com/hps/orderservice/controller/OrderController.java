package com.hps.orderservice.controller;

import com.hps.orderservice.dto.*;
import com.hps.orderservice.entity.Order;
import com.hps.orderservice.entity.OrderLineItem;
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
    public ResponseEntity<Order> updateOrder( @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder( request));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteOrderById() {
        orderService.deleteOrderById();
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/checkout")
    public ResponseEntity<BillResponse> checkout(@RequestBody BillRequest billRequest) {
        BillResponse response = orderService.checkout(billRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/summary")
    public ResponseEntity<List<OrderLineResponse>> summaryCheckoutProducts() {
        List<OrderLineResponse> orderLineResponses = orderService.summaryCheckoutProducts();
        return ResponseEntity.ok(orderLineResponses);
    }

    @PostMapping("/pay-bill")
    public ResponseEntity<String> payBill(@RequestBody String phone) {
        String response = orderService.payBill(phone);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestParam String verificationCode) {
        String response = orderService.confirmPayment(verificationCode);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/create-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo)  {
        String paymentIntent = orderService.createPaymentIntent(paymentInfo);
        return ResponseEntity.ok(paymentIntent);
    }
}
