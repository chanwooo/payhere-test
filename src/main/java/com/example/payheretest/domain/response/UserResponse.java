package com.example.payheretest.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(description = "사용자 정보 응답")
@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String name;

    @Schema(description = "사용자가 마지막으로 로그인한 토큰의 만료시간, 로그아웃시 현재시간으로 변경하여 만료시킴.")
    private Long expiredAt;
}
