package com.codestates.order;

import com.codestates.coffee.Coffee;
import com.codestates.coffee.CoffeeService;
import com.codestates.order.entity.CoffeeRef;
import com.codestates.order.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    default Order orderPostDTOToOrder(OrderDTO.Post orderPostDTO) {
        Order order = new Order();
        order.setMemberId(new AggregateReference.IdOnlyAggregateReference<>(orderPostDTO.getMemberId()));
        // orderCoffees 매핑
        Set<CoffeeRef> orderCoffees = orderPostDTO.getOrderCoffees().stream()
                .map(coffeeDTO -> CoffeeRef.builder().coffeeId(coffeeDTO.getCoffeeId()).quantity(coffeeDTO.getQuantity()).build())
                .collect(Collectors.toSet());
        order.setOrderCoffees(orderCoffees);
        return order;
    }

    default OrderDTO.Response orderToOrderResponseDTO(CoffeeService coffeeService, Order order) {
        OrderDTO.Response orderResponseDTO = new OrderDTO.Response();
        orderResponseDTO.setOrderId(order.getOrderId());
        orderResponseDTO.setMemberId(order.getMemberId().getId());
        orderResponseDTO.setOrderStatus(order.getOrderStatus());
        orderResponseDTO.setCreatedAt(order.getCreatedAt());

        // orderCoffees 매핑
        List<OrderDTO.Response.CoffeeDTO> orderCoffees = order.getOrderCoffees().stream()
                .map(coffeeRef -> {
                    Coffee coffee = coffeeService.findCoffee(coffeeRef.getCoffeeId());
                    return new OrderDTO.Response.CoffeeDTO(
                            coffee.getCoffeeId(),
                            coffee.getKorName(),
                            coffee.getEngName(),
                            coffee.getPrice(),
                            coffeeRef.getQuantity()
                    );
                }).collect(Collectors.toList());
        orderResponseDTO.setOrderCoffees(orderCoffees);
        return orderResponseDTO;
    }
}
