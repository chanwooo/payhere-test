package com.example.payheretest.controller;

import com.example.payheretest.domain.request.AuthRequest;
import com.example.payheretest.domain.response.LoginResponse;
import com.example.payheretest.domain.response.UserResponse;
import com.example.payheretest.security.JwtTokenProvider;
import com.example.payheretest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "회원", description = "회원 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signUp(@Valid @RequestBody final AuthRequest authRequest) {
        return userService.signUp(authRequest);
    }

    @Operation(summary = "로그인", description = "로그인 시 토큰을 생성하고 토큰의 만료시간을 사용자 정보에 저장한다.")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody final AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @Operation(summary = "로그아웃", description = "로그아웃시 사용자가 마지막으로 로그인한 토큰의 만료시간을 현재시간으로 변경하여 만료시킴.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/logout")
    public UserResponse logout(final HttpServletRequest request) {
        final String userEmail = jwtTokenProvider.getUserId(request);
        return userService.logout(userEmail);
    }

    @Operation(summary = "로그인 중인 사용자 정보 조회")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user")
    public UserResponse userInfo(final HttpServletRequest request) {
        final String userEmail = jwtTokenProvider.getUserId(request);
        return userService.getByEmail(userEmail).toResponse();
    }

    @Operation(summary = "로그인 중인 사용자 이름 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/user")
    public UserResponse updateName(final HttpServletRequest request, final String name) {
        final String userEmail = jwtTokenProvider.getUserId(request);
        return userService.updateUserName(userEmail, name);
    }
}
