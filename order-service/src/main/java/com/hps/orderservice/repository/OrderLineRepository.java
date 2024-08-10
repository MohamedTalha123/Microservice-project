package com.hps.orderservice.repository;

import com.hps.orderservice.entity.Order;
import com.hps.orderservice.entity.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineItem,Long> {
    List<OrderLineItem> findAllByOrder(Order order);


}
