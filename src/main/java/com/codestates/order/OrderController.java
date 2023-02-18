package com.codestates.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v10/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper mapper;

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderDTO.Post orderPostDTO) {
        Order order = mapper.orderPostDTOToOrder(orderPostDTO);
        Order response = orderService.createOrder(order);
        return new ResponseEntity<>(mapper.orderToOrderResponseDTO(response), HttpStatus.CREATED);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);
        return new ResponseEntity<>(mapper.orderToOrderResponseDTO(order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders() {
        List<Order> orders = orderService.findOrders();
        List<OrderDTO.Response> response = orders.stream()
                .map(mapper::orderToOrderResponseDTO).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
