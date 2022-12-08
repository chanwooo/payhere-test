package com.example.payheretest.controller;

import com.example.payheretest.domain.request.MoneyBookCreateRequest;
import com.example.payheretest.domain.response.MoneyBookResponse;
import com.example.payheretest.domain.request.MoneyBookUpdateRequest;
import com.example.payheretest.security.JwtTokenProvider;
import com.example.payheretest.service.MoneyBookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/moneybook")
public class MoneyBookController {

    private final MoneyBookService moneyBookService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MoneyBookResponse create(
            final HttpServletRequest request,
            @Valid @RequestBody final MoneyBookCreateRequest moneyBookCreateRequest
    ) {
        String userId = jwtTokenProvider.getUserId(request);

        return moneyBookService.create(userId, moneyBookCreateRequest);
    }

    @PutMapping("{id}")
    MoneyBookResponse putUpdate(
            final HttpServletRequest request,
            @PathVariable final Long id,
            @Valid @RequestBody final MoneyBookUpdateRequest moneyBookUpdateRequest
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.putUpdate(userEmail, id, moneyBookUpdateRequest);
    }

    @PatchMapping("{id}")
    MoneyBookResponse patchUpdate(
            final HttpServletRequest request,
            @PathVariable final Long id,
            @Valid @RequestBody final MoneyBookUpdateRequest moneyBookUpdateRequest
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.patchUpdate(userEmail, id, moneyBookUpdateRequest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            final HttpServletRequest request,
            @PathVariable final Long id
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        moneyBookService.delete(userEmail, id);
    }

    @GetMapping
    List<MoneyBookResponse> list(
            final HttpServletRequest request
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.list(userEmail);
    }

    @GetMapping("{id}")
    MoneyBookResponse get(
            final HttpServletRequest request,
            @PathVariable final Long id
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.get(userEmail, id);
    }
}
