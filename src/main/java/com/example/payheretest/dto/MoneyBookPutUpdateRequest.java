package com.example.payheretest.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MoneyBookPutUpdateRequest {

    @PositiveOrZero
    @NotNull(message = "money는 null일 수 없습니다.")
    private Long money;

    @NotNull(message = "memo는 null일 수 없습니다.")
    private String memo;
}
