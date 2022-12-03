package com.example.payheretest.domain;

import com.example.payheretest.dto.MoneyBookRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookUpdateRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

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


    public static MoneyBook of(User user, MoneyBookRequest moneyBookRequest) {
        return MoneyBook.builder()
                .user(user)
                .money(moneyBookRequest.getMoney())
                .memo(moneyBookRequest.getMemo())
                .build();
    }

    public MoneyBookResponse toResponse() {
        return MoneyBookResponse.builder()
                .id(getId())
                .money(money)
                .memo(memo)
                .created_at(getCreated_at())
                .updated_at(getUpdated_at())
                .build();
    }
    public MoneyBook patchUpdate(MoneyBookUpdateRequest moneyBookUpdateRequest) {
        this.money = moneyBookUpdateRequest.getMoney() != null
                ? moneyBookUpdateRequest.getMoney()
                : money;
        this.memo = moneyBookUpdateRequest.getMemo() != null
                ? moneyBookUpdateRequest.getMemo()
                : memo;
        this.setUpdated_at(LocalDateTime.now());
        return this;
    }

    public MoneyBook putUpdate(MoneyBookUpdateRequest moneyBookUpdateRequest) {
        this.money = moneyBookUpdateRequest.getMoney();
        this.memo = moneyBookUpdateRequest.getMemo();
        return this;
    }
}
