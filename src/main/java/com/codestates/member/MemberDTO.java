package com.codestates.member;

import com.codestates.validator.NotSpace;
import com.codestates.validator.PatchValidation;
import com.codestates.validator.PostValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDTO {


    @Getter
    @Setter
    private static class Request {
        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.",
                groups = {PostValidation.class, PatchValidation.class})
        @NotBlank(message = "휴대폰 번호는 공백이 아니어야 합니다.", groups = PostValidation.class)
        private String phone;

        @NotBlank(message = "회원 이름은 공백이 아니어야 합니다.", groups = PostValidation.class)
        @NotSpace(message = "회원 이름은 공백이 아니어야 합니다.", groups = PatchValidation.class)
        private String name;
    }
    @Getter
    @Setter
    public static class Post extends Request {
        @NotBlank(groups = PostValidation.class)
        @Email(groups = PostValidation.class)
        private String email;

    }

    @Getter
    @Setter
    public static class Patch extends Request {
        private long memberId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long memberId;
        private String email;
        private String name;
        private String phone;
    }
}
