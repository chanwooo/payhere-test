package com.example.payheretest.service;

import com.example.payheretest.dto.MoneyBookCreateRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookUpdateRequest;

import java.util.List;

public interface MoneyBookService {

    MoneyBookResponse create(MoneyBookCreateRequest moneyBookCreateRequest);
    MoneyBookResponse patchUpdate(long moneyBookId, MoneyBookUpdateRequest moneyBookUpdateRequest);
    MoneyBookResponse putUpdate(long moneyBookId, MoneyBookUpdateRequest moneyBookUpdateRequest);
    void delete(long moneyBookId);
    List<MoneyBookResponse> list();
    MoneyBookResponse get(long moneyBookId);
}
