package com.codestates.coffee;

import com.codestates.validator.NotSpace;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CoffeeDTO {

    private static class Request {

    }

    @Getter
    @Setter
    public static class Post extends Request {
        @NotBlank
        private String korName;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z]+(\\s?[A-Za-z]+)*$", message = "영어 이름은 영어 알파벳만으로 이루어져야 합니다.")
        private String engName;
        @Range(min = 100,max = 50000)
        private int price;
    }

    @Getter
    @Setter
    public static class Patch extends Request {
        private long coffeeId;
        @NotSpace
        private String korName;
        @Pattern(regexp = "^[A-Za-z]+(\\s?[A-Za-z]+)*$", message = "영어 이름은 영어 알파벳만으로 이루어져야 합니다.")
        private String engName;
        @Range(min = 100,max = 50000)
        private Integer price;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long coffeeId;
        private String korName;
        private String engName;
        private Integer price;
    }
}
