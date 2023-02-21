package com.codestates.order;

import com.codestates.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    @Getter
    @Setter
    public static class Post {
        @Positive(message = "memberId는 0보다 큰 값이어야 합니다.")
        private long memberId;

        @Valid
        @NotEmpty(message = "orderCoffees 배열이 있어야 합니다.")
        private List<@Valid CoffeeDTO> orderCoffees;

        @Getter
        @AllArgsConstructor
        public static class CoffeeDTO {
            @Positive
            private long coffeeId;
            @Positive
            private int quantity;
        }

    }

    @Getter
    @Setter
    public static class Response {
        private long orderId;
        private long memberId;
        private Order.OrderStatus orderStatus;
        private List<CoffeeDTO> orderCoffees;
        private LocalDateTime createdAt;

        @Getter
        @AllArgsConstructor
        public static class CoffeeDTO {
            private long coffeeId;
            private String korName;
            private String engName;
            private int price;
            private int quantity;

        }
    }

}
