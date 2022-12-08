package com.example.payheretest.controller;

import com.example.payheretest.domain.request.MoneyBookCreateRequest;
import com.example.payheretest.domain.request.MoneyBookPatchUpdateRequest;
import com.example.payheretest.domain.request.MoneyBookPutUpdateRequest;
import com.example.payheretest.domain.response.MoneyBookResponse;
import com.example.payheretest.security.JwtTokenProvider;
import com.example.payheretest.service.MoneyBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "가계부", description = "가계부 API")
@RequiredArgsConstructor
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/moneybook")
public class MoneyBookController {

    private final MoneyBookService moneyBookService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "새로운 기록을 추가")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MoneyBookResponse create(
            final HttpServletRequest request,
            @Valid @RequestBody final MoneyBookCreateRequest moneyBookCreateRequest
    ) {
        String userId = jwtTokenProvider.getUserId(request);

        return moneyBookService.create(userId, moneyBookCreateRequest);
    }

    @Operation(summary = "기록을 수정")
    @PutMapping("{id}")
    MoneyBookResponse putUpdate(
            final HttpServletRequest request,
            @Parameter(description = "수정할 기록의 id")
            @PathVariable final Long id,
            @Valid @RequestBody final MoneyBookPutUpdateRequest moneyBookPutUpdateRequest
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.putUpdate(userEmail, id, moneyBookPutUpdateRequest);
    }

    @Operation(summary = "기록을 부분 수정")
    @PatchMapping("{id}")
    MoneyBookResponse patchUpdate(
            final HttpServletRequest request,
            @Parameter(description = "부분 수정할 기록의 id")
            @PathVariable final Long id,
            @Valid @RequestBody final MoneyBookPatchUpdateRequest moneyBookPatchUpdateRequest
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.patchUpdate(userEmail, id, moneyBookPatchUpdateRequest);
    }

    @Operation(summary = "기록을 삭제")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            final HttpServletRequest request,
            @Parameter(description = "삭제할 기록의 id")
            @PathVariable final Long id
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        moneyBookService.delete(userEmail, id);
    }

    @Operation(summary = "사용자의 기록 목록")
    @GetMapping
    List<MoneyBookResponse> list(
            final HttpServletRequest request
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.list(userEmail);
    }

    @Operation(summary = "사용자의 특정 기록 조회")
    @GetMapping("{id}")
    MoneyBookResponse get(
            final HttpServletRequest request,
            @Parameter(description = "조회할 기록의 id")
            @PathVariable final Long id
    ) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return moneyBookService.get(userEmail, id);
    }
}
