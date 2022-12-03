package com.example.payheretest.service;

import com.example.payheretest.dto.MoneyBookCreateRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookUpdateRequest;

import java.util.List;

public interface MoneyBookService {

    MoneyBookResponse create(MoneyBookCreateRequest moneyBookCreateRequest);
    MoneyBookResponse patchUpdate(Long moneyBookId, MoneyBookUpdateRequest moneyBookUpdateRequest);
    MoneyBookResponse putUpdate(Long moneyBookId, MoneyBookUpdateRequest moneyBookUpdateRequest);
    void delete(Long moneyBookId);
    List<MoneyBookResponse> list();
    MoneyBookResponse get(Long moneyBookId);
}
