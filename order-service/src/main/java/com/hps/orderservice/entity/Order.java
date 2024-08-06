package com.hps.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String reference;
    private BigDecimal totalAmount;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime lastModifiedDate;


    public void addOrderLineItem(OrderLineItem orderLineItem) {
        orderLineItems.add(orderLineItem);
        orderLineItem.setOrder(this);
    }

    public void removeOrderLineItem(OrderLineItem orderLineItem) {
        orderLineItems.remove(orderLineItem);
        orderLineItem.setOrder(null);
    }

}
