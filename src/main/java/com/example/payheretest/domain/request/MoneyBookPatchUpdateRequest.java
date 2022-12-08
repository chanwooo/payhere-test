package com.example.payheretest.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Schema(description = "부분 수정 요청, 일부 필드만 요청시 기존값은 그대로두고 변경된 부분만 수정합니다.")
@Data
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MoneyBookPatchUpdateRequest {

    @PositiveOrZero
    private Long money;

    private String memo;
}
