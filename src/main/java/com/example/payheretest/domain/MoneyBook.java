package com.example.payheretest.domain;

import com.example.payheretest.dto.MoneyBookPatchUpdateRequest;
import com.example.payheretest.dto.MoneyBookPutUpdateRequest;
import com.example.payheretest.dto.MoneyBookRequest;
import com.example.payheretest.dto.MoneyBookResponse;
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
    public MoneyBook patchUpdate(MoneyBookPatchUpdateRequest moneyBookPatchUpdateRequest) {
        this.money = moneyBookPatchUpdateRequest.getMoney() != null
                ? moneyBookPatchUpdateRequest.getMoney()
                : money;
        this.memo = moneyBookPatchUpdateRequest.getMemo() != null
                ? moneyBookPatchUpdateRequest.getMemo()
                : memo;
        this.setUpdated_at(LocalDateTime.now());
        return this;
    }

    public MoneyBook putUpdate(MoneyBookPutUpdateRequest moneyBookPutUpdateRequest) {
        this.money = moneyBookPutUpdateRequest.getMoney();
        this.memo = moneyBookPutUpdateRequest.getMemo();
        return this;
    }
}
