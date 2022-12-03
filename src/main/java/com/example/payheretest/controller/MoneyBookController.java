package com.example.payheretest.controller;

import com.example.payheretest.dto.MoneyBookCreateRequest;
import com.example.payheretest.dto.MoneyBookPatchUpdateRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookPutUpdateRequest;
import com.example.payheretest.service.MoneyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/moneybook")
public class MoneyBookController {

    private final MoneyBookService moneyBookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MoneyBookResponse create(@Valid final @RequestBody MoneyBookCreateRequest moneyBookCreateRequest) {
        return moneyBookService.create(moneyBookCreateRequest);
    }

    @PutMapping("{id}")
    MoneyBookResponse putUpdate(@PathVariable final Long id,
                             @Valid final @RequestBody MoneyBookPutUpdateRequest moneyBookPutUpdateRequest) {
        return moneyBookService.putUpdate(id, moneyBookPutUpdateRequest);
    }

    @PatchMapping("{id}")
    MoneyBookResponse patchUpdate(@PathVariable Long id,
                             @Valid final @RequestBody MoneyBookPatchUpdateRequest moneyBookPatchUpdateRequest) {
        return moneyBookService.patchUpdate(id, moneyBookPatchUpdateRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable final Long id) {
        moneyBookService.delete(id);
    }

    @GetMapping
    List<MoneyBookResponse> list() {
        return moneyBookService.list();
    }

    @GetMapping("{id}")
    MoneyBookResponse get(@PathVariable final Long id) {
        return moneyBookService.get(id);
    }
}
