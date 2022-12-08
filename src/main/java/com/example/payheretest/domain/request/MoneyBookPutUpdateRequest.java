package com.example.payheretest.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Schema(description = "수정 요청, 모든 필드가 유효해야합니다.(Not Null)")
@Data
@Setter(AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MoneyBookPutUpdateRequest {

    @PositiveOrZero
    @NotNull
    private Long money;

    @NotNull
    private String memo;
}
