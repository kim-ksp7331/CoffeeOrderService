package com.codestates.order;

import com.codestates.coffee.CoffeeService;
import com.codestates.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v10/orders")
@Validated
public class OrderController {

    private final static String ORDER_DEFAULT_URL = "/v10/orders";
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final CoffeeService coffeeService;

    public OrderController(OrderService orderService, OrderMapper mapper, CoffeeService coffeeService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.coffeeService = coffeeService;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderDTO.Post orderPostDTO) {
        Order order = orderService.createOrder(mapper.orderPostDTOToOrder(orderPostDTO));

        URI location = UriComponentsBuilder
                .newInstance()
                .path(ORDER_DEFAULT_URL + "/{order-id}")
                .buildAndExpand(order.getOrderId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);
        return new ResponseEntity<>(mapper.orderToOrderResponseDTO(coffeeService, order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders() {
        List<Order> orders = orderService.findOrders();
        List<OrderDTO.Response> response = orders.stream()
                .map(order -> mapper.orderToOrderResponseDTO(coffeeService, order)).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@Positive @PathVariable("order-id") long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
