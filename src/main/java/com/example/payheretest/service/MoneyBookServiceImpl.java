package com.example.payheretest.service;

import com.example.payheretest.domain.MoneyBook;
import com.example.payheretest.domain.User;
import com.example.payheretest.dto.MoneyBookCreateRequest;
import com.example.payheretest.dto.MoneyBookRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookUpdateRequest;
import com.example.payheretest.exception.NoSuchMoneyBookException;
import com.example.payheretest.repository.MoneyBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyBookServiceImpl implements MoneyBookService {

    private final MoneyBookRepository moneyBookRepository;


    @Override
    @Transactional
    public MoneyBookResponse create(MoneyBookCreateRequest moneyBookCreateRequest) {
        MoneyBookRequest moneyBookRequest = MoneyBookRequest.builder()
                .money(moneyBookCreateRequest.getMoney())
                .memo(moneyBookCreateRequest.getMemo())
                .build();
        MoneyBook moneyBook = MoneyBook.of(new User(), moneyBookRequest);
        return moneyBookRepository.save(moneyBook).toResponse();
    }

    @Override
    @Transactional
    public MoneyBookResponse patchUpdate(long moneyBookId, MoneyBookUpdateRequest moneyBookUpdateRequest) {
        MoneyBook moneyBook = moneyBookRepository.findById(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);

        return moneyBook.patchUpdate(moneyBookUpdateRequest).toResponse();
    }

    @Override
    @Transactional
    public MoneyBookResponse putUpdate(long moneyBookId, MoneyBookUpdateRequest moneyBookUpdateRequest) {
        MoneyBook moneyBook = moneyBookRepository.findById(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);

        return moneyBook.putUpdate(moneyBookUpdateRequest).toResponse();
    }

    @Override
    @Transactional
    public void delete(long moneyBookId) {
        MoneyBook moneyBook = moneyBookRepository.findByIdAndDeletedIsFalse(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new);
        moneyBook.delete();
    }

    @Override
    public List<MoneyBookResponse> list() {
        return moneyBookRepository.findAllByDeletedIsFalse()
                .stream()
                .map(MoneyBook::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MoneyBookResponse get(long moneyBookId) {
        return moneyBookRepository.findByIdAndDeletedIsFalse(moneyBookId)
                .orElseThrow(NoSuchMoneyBookException::new)
                .toResponse();
    }
}
