package com.example.payheretest.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@Builder
public class MoneyBookRequest {

    private Long userId;
    private Long money;
    private String memo;
}
