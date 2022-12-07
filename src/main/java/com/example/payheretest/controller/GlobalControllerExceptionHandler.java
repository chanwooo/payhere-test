package com.example.payheretest.controller;

import com.example.payheretest.exception.InvalidMoneyBookException;
import com.example.payheretest.exception.NoSuchMoneyBookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;

@Slf4j
@RestControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(NoSuchMoneyBookException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "거래기록을 찾지 못했어요.")
    public void handleMoneyBookNotFound() {
        log.error("404, 거래기록을 찾지 못했어요.");
    }

    @ExceptionHandler(InvalidMoneyBookException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "해당 거래에 권한이 없어요.")
    public void handleMoneyBookEmailInvalid() {
        log.error("403, 해당 거래에 권한이 없어요.");
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "입력이 잘못되었어요 확인해주세요.")
    public void handleUnexpectedType() {
        log.error("400, 입력이 잘못되었어요 확인해주세요.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "bad request")
    public void handleIllegalArgument() {
        log.error("400, bad request");
    }
}