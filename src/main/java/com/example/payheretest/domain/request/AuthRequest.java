package com.example.payheretest.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Schema(description = "로그인 요청, 이메일형식의 아이디와 패스워드가 필요합니다.")
@Data
@Builder
public class AuthRequest {
    @Schema(description = "로그인 아이디(이메일 형식)", example = "testuser@payhere.in")
    @NotNull
    @Email
    private String id;

    @Schema(description = "비밀번호", example = "password1234")
    @NotNull
    private String password;
}
