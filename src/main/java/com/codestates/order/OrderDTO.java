package com.codestates.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

public class OrderDTO {

    @Getter
    @Setter
    public static class Post {
        @Positive(message = "memberId는 0보다 큰 값이어야 합니다.")
        private long memberId;
        @Positive(message = "coffeeId는 0보다 큰 값이어야 합니다.")
        private long coffeeId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long memberId;
        private long coffeeId;
    }

}
