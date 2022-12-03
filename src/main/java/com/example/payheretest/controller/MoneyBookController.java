package com.example.payheretest.controller;

import com.example.payheretest.dto.MoneyBookCreateRequest;
import com.example.payheretest.dto.MoneyBookResponse;
import com.example.payheretest.dto.MoneyBookUpdateRequest;
import com.example.payheretest.service.MoneyBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/moneybook")
public class MoneyBookController {

    private final MoneyBookService moneyBookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MoneyBookResponse create(@Valid final @RequestBody MoneyBookCreateRequest moneyBookCreateRequest) {
        log.info("moneyBookCreateRequest = " + moneyBookCreateRequest);
        return moneyBookService.create(moneyBookCreateRequest);
    }

    @PutMapping({"id"})
    MoneyBookResponse putUpdate(@RequestParam final long id,
                             @Valid final @RequestBody MoneyBookUpdateRequest moneyBookUpdateRequest) {
        return moneyBookService.putUpdate(id, moneyBookUpdateRequest);
    }

    @PatchMapping({"id"})
    MoneyBookResponse patchUpdate(@RequestParam long id,
                             @Valid final @RequestBody MoneyBookUpdateRequest moneyBookUpdateRequest) {
        return moneyBookService.patchUpdate(id, moneyBookUpdateRequest);
    }

    @DeleteMapping({"id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestParam final long id) {
        moneyBookService.delete(id);
    }

    @GetMapping
    List<MoneyBookResponse> list() {
        return moneyBookService.list();
    }

    @GetMapping({"id"})
    MoneyBookResponse get(@RequestParam final long id) {
        return moneyBookService.get(id);
    }
}
