package com.example.payheretest.service;

import com.example.payheretest.domain.entity.MoneyBook;
import com.example.payheretest.domain.entity.User;
import com.example.payheretest.domain.request.MoneyBookCreateRequest;
import com.example.payheretest.domain.request.MoneyBookUpdateRequest;
import com.example.payheretest.domain.response.MoneyBookResponse;
import com.example.payheretest.repository.MoneyBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MoneyBookServiceTest {

    @InjectMocks
    MoneyBookService moneyBookService;

    @Mock
    MoneyBookRepository moneyBookRepository;

    @Mock
    UserService userService;

    final String userEmail = "test@test.test";
    final long money = 10000L;
    final String memo = "memo";

    final User user = User.builder()
            .email(userEmail)
            .build();

    @Test
    void create() {
        //given
        MoneyBookCreateRequest moneyBookCreateRequest = MoneyBookCreateRequest.builder()
                .money(money)
                .memo(memo)
                .build();

        MoneyBook moneyBook = MoneyBook.builder()
                .user(user)
                .money(money)
                .memo(memo)
                .build();

        when(userService.getByEmail(anyString())).thenReturn(user);
        when(moneyBookRepository.save(any())).thenReturn(moneyBook);

        //when
        MoneyBookResponse moneyBookResponse = moneyBookService.create(userEmail, moneyBookCreateRequest);
        log.info("moneyBookResponse = " + moneyBookResponse);

        //then
        assertThat(moneyBookResponse.getMoney()).isEqualTo(money);
        assertThat(moneyBookResponse.getMemo()).isEqualTo(memo);
    }

    @Test
    void patchUpdate() {
        //given
        long updateMoney = 100000L;
        String updateMemo = "update memo";

        MoneyBookUpdateRequest moneyBookUpdateRequest = MoneyBookUpdateRequest.builder()
                .money(updateMoney)
                .memo(updateMemo)
                .build();

        MoneyBook moneyBook = MoneyBook.builder()
                .user(user)
                .money(money)
                .memo(memo)
                .build();
        moneyBook.setId(1L);

        when(moneyBookRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.of(moneyBook));

        //when
        MoneyBookResponse moneyBookResponse = moneyBookService.patchUpdate(userEmail, 1L, moneyBookUpdateRequest);
        log.info("moneyBookResponse = " + moneyBookResponse);
        //then
        assertThat(moneyBookResponse.getMoney()).isEqualTo(updateMoney);
        assertThat(moneyBookResponse.getMemo()).isEqualTo(updateMemo);
    }

    @Test
    void putUpdate() {
        //given
        long updateMoney = 100000L;
        String updateMemo = "update memo";

        MoneyBookUpdateRequest moneyBookUpdateRequest = MoneyBookUpdateRequest.builder()
                .money(updateMoney)
                .memo(updateMemo)
                .build();

        MoneyBook moneyBook = MoneyBook.builder()
                .user(user)
                .money(money)
                .memo(memo)
                .build();
        moneyBook.setId(1L);

        when(moneyBookRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.of(moneyBook));

        //when
        MoneyBookResponse moneyBookResponse = moneyBookService.putUpdate(userEmail, 1L, moneyBookUpdateRequest);
        log.info("moneyBookResponse = " + moneyBookResponse);
        //then
        assertThat(moneyBookResponse.getMoney()).isEqualTo(updateMoney);
        assertThat(moneyBookResponse.getMemo()).isEqualTo(updateMemo);
    }

    @Test
    void delete() {
        //given
        MoneyBook moneyBook = MoneyBook.builder()
                .user(user)
                .money(money)
                .memo(memo)
                .build();
        moneyBook.setId(1L);

        when(moneyBookRepository.findByIdAndDeletedIsFalse(anyLong())).thenReturn(Optional.of(moneyBook));

        //when
        moneyBookService.delete(userEmail, 1L);
        log.info("moneyBook.isDeleted() = " + moneyBook.isDeleted());
        log.info("moneyBook.getDeletedAt() = " + moneyBook.getDeletedAt());

        //then
        assertThat(moneyBook.isDeleted()).isEqualTo(true);
        assertThat(moneyBook.getDeletedAt()).isBefore(LocalDateTime.now());

    }

    @Test
    void list() {
        //given
        int testMoneyBookCount = 3;
        List<MoneyBook> moneyBooks = new ArrayList<>();
        for (long i = 1; i <= testMoneyBookCount; i++) {
            MoneyBook moneyBook = MoneyBook.builder()
                    .user(user)
                    .money(money * i)
                    .memo(memo + i)
                    .build();
            moneyBook.setId(i);
            moneyBooks.add(moneyBook);
        }

        when(moneyBookRepository.findAllByUserEmailAndDeletedIsFalse(userEmail)).thenReturn(moneyBooks);

        //when
        List<MoneyBookResponse> list = moneyBookService.list(userEmail);

        list.forEach(moneyBookResponse -> log.info("moneyBookResponse = id=" + moneyBookResponse.getId() + ", money=" + moneyBookResponse.getMoney() + ", memo=" + moneyBookResponse.getMemo()));
        //then
        assertThat(list.size()).isEqualTo(testMoneyBookCount);
        for (int i = 0; i < testMoneyBookCount; i++) {
            long targetId = i+1;
            assertThat(list.get(i).getId()).isEqualTo(targetId);
            assertThat(list.get(i).getMoney()).isEqualTo(10000 * targetId);
            assertThat(list.get(i).getMemo()).isEqualTo("memo" + targetId);
        }
    }

    @Test
    void get() {
        //given
        MoneyBook moneyBook = MoneyBook.builder()
                .user(user)
                .money(money)
                .memo(memo)
                .build();
        moneyBook.setId(1L);

        when(moneyBookRepository.findByIdAndDeletedIsFalse(1L)).thenReturn(Optional.of(moneyBook));

        //when
        MoneyBookResponse response = moneyBookService.get(userEmail, 1L);
        log.info("response = " + response);

        //then
        assertThat(response.getMoney()).isEqualTo(money);
        assertThat(response.getMemo()).isEqualTo(memo);
    }
}