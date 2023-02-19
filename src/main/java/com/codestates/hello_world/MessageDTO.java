package com.codestates.hello_world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MessageDTO {
    @Getter
    public static class Post {
        @NotBlank
        private String message;
    }

    @Getter
    @Setter
    public static class Response {
        private long messageId;
        private String message;
    }
}
