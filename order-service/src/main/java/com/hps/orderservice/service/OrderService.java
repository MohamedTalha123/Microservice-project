package com.hps.orderservice.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hps.orderservice.dto.*;
import com.hps.orderservice.entity.Order;
import com.hps.orderservice.entity.OrderLineItem;
import com.hps.orderservice.entity.PaymentMethod;
import com.hps.orderservice.exception.OrderException;
import com.hps.orderservice.http.PaymentClient;
import com.hps.orderservice.http.ProductClient;
import com.hps.orderservice.http.UserClient;
import com.hps.orderservice.mapper.OrderLineMapper;
import com.hps.orderservice.repository.OrderLineRepository;
import com.hps.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    public static Order order;

    @Transactional
    public Order createOrder(OrderRequest request) {
        this.userClient.findUserById(request.user_id())
                .orElseThrow(() -> new OrderException("Cannot create order :: No user exists with the provided ID"));

        var purchaseRequest = PurchaseRequest.builder()
                .product_id(request.product_id())
                .quantity(request.quantity())
                .build();

        Boolean isProductAvailable = this.productClient.checkProductAvailability(purchaseRequest);
        if (!isProductAvailable) {
            throw new RuntimeException("Product out of stock");
        }
        // Fetch product details to get the price

        var product = this.productClient.getProductById(request.product_id())
                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (order == null) {
            order = Order.builder()
                    .userId(request.user_id())
                    .reference(generateReference())
                    .lastModifiedDate(LocalDateTime.now())
                    .paymentMethod(PaymentMethod.STRIPE)
                    .totalAmount(BigDecimal.ZERO)
                    .build();
            this.orderRepository.save(order);

        }
        OrderLineItem orderLineItem = OrderLineItem.builder()
                .order(order)
                .price(product.getPrice().multiply(BigDecimal.valueOf(request.quantity())))
                .quantity(request.quantity())
                .productReference(product.getReference())
                .productId(request.product_id())
                .build();

        this.orderLineRepository.save(orderLineItem);

        order.setTotalAmount(order.getTotalAmount().add(orderLineItem.getPrice()));

        return this.orderRepository.save(order);
    }

    // TODO: Implement the payment process
    // TODO: Call the updateProductsQuantity after the payment
    // TODO: Send the order confirmation through notification-service
    private String generateReference() {
        String ref = UUID.randomUUID().toString().substring(0, 8);
        return "CMD-".concat(ref);
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("Order not found with id: " + id));
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrder(OrderRequest request) {

        var product = this.productClient.getProductById(request.product_id())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        System.out.println("xxx:" + order.getId());
        List<OrderLineItem> orderLineByOrder = orderLineRepository.findAllByOrder(order);

        OrderLineItem orderLineItem = orderLineByOrder.stream()
                .filter(item -> item.getProductId().equals(request.product_id()))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("OrderLineItem not found"));


        orderLineItem.setQuantity(request.quantity());
        orderLineItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.quantity())));
        if (request.quantity() == 0) {
            this.orderLineRepository.delete(orderLineItem);
        }
        this.orderLineRepository.save(orderLineItem);

        BigDecimal totalAmount = orderLineByOrder
                .stream()
                .map(OrderLineItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrderById() {
        orderRepository.deleteById(order.getId());
        order = null;
    }

    public BillResponse checkout(BillRequest billRequest) {
        return paymentClient.createBill(billRequest);
    }
    public List<OrderLineResponse> summaryCheckoutProducts(){

        List<OrderLineResponse> orderLineItemsProducts = new ArrayList<>();

        List<OrderLineItem> orderLineItems = orderLineRepository.findAllByOrder(order);

        Map<Long, OrderLineItem> orderLineMap = orderLineItems
                .stream()
                .collect(Collectors.toMap(OrderLineItem::getProductId, orderLineItem -> orderLineItem));




        List<ProductResponse> products = productClient.findAllById(orderLineMap.keySet()).getBody();
        System.out.println("PRODUCTS ::::"+products);
        Map<Long, ProductResponse> productMap = products.stream()
                .collect(Collectors.toMap(ProductResponse::getId, product -> product));

        // Combine the data and build OrderLineResponse objects
        for (Long productId : orderLineMap.keySet()) {
            OrderLineItem orderLineItem = orderLineMap.get(productId);
            ProductResponse productResponse = productMap.get(productId);

            OrderLineResponse orderLineResponse = OrderLineResponse.builder()
                    .orderId(order.getId())
                    .productReference(productResponse.getReference())
                    .name(productResponse.getName())
                    .quantity(orderLineItem.getQuantity())
                    .unitPrice(productResponse.getPrice())
                    .totalPrice(productResponse.getPrice().multiply(BigDecimal.valueOf(orderLineItem.getQuantity())))
                    .build();

            orderLineItemsProducts.add(orderLineResponse);
        }

        return orderLineItemsProducts;
    }
}
