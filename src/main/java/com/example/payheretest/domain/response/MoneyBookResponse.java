package com.example.payheretest.domain.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter(AccessLevel.NONE)
@Builder
public class MoneyBookResponse {
    private Long id;
    private Long money;
    private String memo;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
