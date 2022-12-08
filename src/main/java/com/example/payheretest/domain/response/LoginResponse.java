package com.example.payheretest.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(description = "로그인 응답, 로그인에 성공한 사용자의 이메일과 토큰")
@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String userId;
    private String token;
}
