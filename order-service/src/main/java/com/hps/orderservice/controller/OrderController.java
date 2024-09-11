package com.hps.orderservice.controller;

import com.hps.orderservice.dto.*;
import com.hps.orderservice.entity.Order;
import com.hps.orderservice.entity.OrderLineItem;
import com.hps.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        orderService.deleteShoppingItems();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/line-items")
    public ResponseEntity<List<OrderLineItemResponse>> getOrderLineItems(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderLineItemsByOrderId(id));
    }
    @GetMapping("/getCurrentOrder")
    public ResponseEntity<Order> getCurrentOrder(){
       return ResponseEntity.ok(orderService.getCurrentOrder()) ;
    }
    @GetMapping("/getCurrentOrderLineItems")
    public ResponseEntity<List<OrderLineItemResponse>> getOrderLineItemsByOrder(){
        return ResponseEntity.ok(orderService.getOrderLineItemsByOrder()) ;
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
    public ResponseEntity<Map<String,String>> payBill(@RequestBody String phone) {
        Map<String,String> response = orderService.payBill(phone);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<Map<String,String>> confirmPayment(@RequestBody String verificationCode) {
        Map<String,String> response = orderService.confirmPayment(verificationCode);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/create-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo)  {
        String paymentIntent = orderService.createPaymentIntent(paymentInfo);
        return ResponseEntity.ok(paymentIntent);
    }
}
