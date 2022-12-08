package com.example.payheretest.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AuthRequest {
    @Schema(example = "testuser@payhere.in")
    @NotNull
    @Email
    private String id;

    @Schema(example = "password1234")
    @NotNull
    private String password;
}
