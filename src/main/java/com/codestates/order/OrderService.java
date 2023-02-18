package com.codestates.order;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public Order createOrder(Order order) {
        System.out.println("OrderService.createOrder");
        Order createdOrder = order;
        return createdOrder;
    }

    public Order findOrder(long orderId) {
        System.out.println("OrderService.findOrder");
        // 검색 로직 추후 작성
        Order order = new Order( 1, 1);
        return order;
    }

    public List<Order> findOrders() {
        System.out.println("OrderService.findOrders");
        List<Order> orders = List.of(
                new Order(1, 1),
                new Order(1, 2)
        );
        return orders;
    }
}
