package com.hps.orderservice.service;


import com.hps.orderservice.dto.OrderRequest;
import com.hps.orderservice.dto.PurchaseRequest;
import com.hps.orderservice.entity.Order;
import com.hps.orderservice.entity.OrderLineItem;
import com.hps.orderservice.entity.PaymentMethod;
import com.hps.orderservice.exception.OrderException;
import com.hps.orderservice.http.ProductClient;
import com.hps.orderservice.http.UserClient;
import com.hps.orderservice.repository.OrderLineRepository;
import com.hps.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    public static Order cart;
    @Transactional
    public Order createOrder(OrderRequest request) {
        var user = this.userClient.findUserById(request.user_id())
                .orElseThrow(() -> new OrderException("Cannot create order :: No user exists with the provided ID"));

        // Build PurchaseRequest to check product availability
        var purchaseRequest = PurchaseRequest.builder()
                .product_id(request.product_id())
                .quantity(request.quantity())
                .build();

        // Check if the product is available
        Boolean isProductAvailable = this.productClient.checkProductAvailability(purchaseRequest);
        if (!isProductAvailable) {
            throw new RuntimeException("Product out of stock");
        }

        // Initialize a new Order if necessary
        if (cart == null) {
            cart = Order.builder()
                    .userId(request.user_id())
                    .reference("REF")
                    .paymentMethod(PaymentMethod.Stripe)
                    .totalAmount(BigDecimal.ZERO)
                    .build();
        }

        // Create OrderLineItem
        OrderLineItem orderLineItem = OrderLineItem.builder()
                .order(cart)
                .price(request.amount().multiply(BigDecimal.valueOf(request.quantity())))
                .quantity(request.quantity())
                .productId(request.product_id())
                .build();

        // Save OrderLineItem
        this.orderLineRepository.save(orderLineItem);

        // Update cart total amount
        cart.setTotalAmount(cart.getTotalAmount().add(orderLineItem.getPrice()));

        // Save and return the order
        return this.orderRepository.save(cart);
    }
    // TODO: Implement the payment process
    // TODO: Call the updateProductsQuantity after the payment
    // TODO: Send the order confirmation through notification-service







}
