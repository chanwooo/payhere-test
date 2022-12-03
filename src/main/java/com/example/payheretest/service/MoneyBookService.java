package com.example.payheretest.service;

import com.example.payheretest.dto.MoneyBookCreateRequest;
import com.example.payheretest.dto.MoneyBookPatchUpdateRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookPutUpdateRequest;

import java.util.List;

public interface MoneyBookService {

    MoneyBookResponse create(MoneyBookCreateRequest moneyBookCreateRequest);
    MoneyBookResponse patchUpdate(Long moneyBookId, MoneyBookPatchUpdateRequest moneyBookpatchUpdateRequest);
    MoneyBookResponse putUpdate(Long moneyBookId, MoneyBookPutUpdateRequest moneyBookPutUpdateRequest);
    void delete(Long moneyBookId);
    List<MoneyBookResponse> list();
    MoneyBookResponse get(Long moneyBookId);
}
