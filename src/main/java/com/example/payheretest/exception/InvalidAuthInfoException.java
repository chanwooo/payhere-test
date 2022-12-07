package com.example.payheretest.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
public class InvalidAuthInfoException extends RuntimeException {

    private String detail;

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getDescription() {
        return "Invalid data related to authentication or authorization. " + detail;
    }
}
