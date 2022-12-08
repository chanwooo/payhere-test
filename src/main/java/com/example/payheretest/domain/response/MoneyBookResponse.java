package com.example.payheretest.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "거래 기록의 응답")
@Data
@Setter(AccessLevel.NONE)
@Builder
public class MoneyBookResponse {
    private Long id;
    private Long money;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
