package com.example.payheretest.domain.entity;

import com.example.payheretest.domain.request.MoneyBookUpdateRequest;
import com.example.payheretest.domain.response.MoneyBookResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Setter(AccessLevel.NONE)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MoneyBook extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private Long money;
    private String memo;

    public MoneyBookResponse toResponse() {
        return MoneyBookResponse.builder()
                .id(getId())
                .money(money)
                .memo(memo)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
    public MoneyBook patchUpdate(MoneyBookUpdateRequest moneyBookUpdateRequest) {
        super.update();
        this.money = moneyBookUpdateRequest.getMoney() != null
                ? moneyBookUpdateRequest.getMoney()
                : money;
        this.memo = moneyBookUpdateRequest.getMemo() != null
                ? moneyBookUpdateRequest.getMemo()
                : memo;
        return this;
    }

    public MoneyBook putUpdate(MoneyBookUpdateRequest moneyBookUpdateRequest) {
        super.update();
        this.money = moneyBookUpdateRequest.getMoney();
        this.memo = moneyBookUpdateRequest.getMemo();
        return this;
    }
}
