package com.codestates.coffee;

import com.codestates.validator.NotSpace;
import com.codestates.validator.PatchValidation;
import com.codestates.validator.PostValidation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CoffeeDTO {

    @Getter
    @Setter
    private static class Request {
        @NotBlank(message = "커피명(한글)은 공백이 아니어야 합니다.", groups = PostValidation.class)
        @NotSpace(message = "커피명(한글)은 공백이 아니어야 합니다.", groups = PatchValidation.class)
        private String korName;

        @NotBlank(message = "커피명(영문)은 공백이 아니어야 합니다.",groups = PostValidation.class)
        @Pattern(regexp = "^[A-Za-z]+(\\s?[A-Za-z]+)*$",
                message = "커피명(영문)은 영문이어야 합니다(단어 사이 공백 한 칸 가능).",
                groups = {PostValidation.class, PatchValidation.class})
        private String engName;

        @Range(min = 100,max = 50000, groups = {PostValidation.class, PatchValidation.class})
        @NotNull(message = "가격이 요청에 포함되어야 합니다.", groups = PostValidation.class)
        private Integer price;

    }

    @Getter
    @Setter
    public static class Post extends Request {
        @NotBlank(groups = PostValidation.class)
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "커피 코드는 3자리 영문이어야 합니다.",
                groups = PostValidation.class)
        private String coffeeCode;
    }

    @Getter
    @Setter
    public static class Patch extends Request {
        private long coffeeId;

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
