package com.codestates.order;

import com.codestates.coffee.CoffeeService;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.MemberService;
import com.codestates.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final CoffeeService coffeeService;
    public Order createOrder(Order order) {
        // 회원이 존재하는지 확인
        memberService.findVerifiedMember(order.getMemberId().getId());
        // 커피들이 존재하는지 확인
        order.getOrderCoffees().stream()
                .forEach(coffeeRef -> coffeeService.findVerifiedCoffee(coffeeRef.getCoffeeId()));
        return orderRepository.save(order);
    }

    public Order findOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

    public List<Order> findOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public void cancelOrder(long orderId) {
        Order findOrder = findVerifiedOrder(orderId);
        int step = findOrder.getOrderStatus().getStepNumber();
        // 주문 요청 단계에서만 주문 취소 가능
        if (step >= 2) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        orderRepository.save(findOrder);
    }

    private Order findVerifiedOrder(long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order findOrder = optionalOrder.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrder;
    }
}
