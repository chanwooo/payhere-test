package com.example.payheretest.controller;

import com.example.payheretest.domain.request.AuthRequest;
import com.example.payheretest.domain.response.LoginResponse;
import com.example.payheretest.domain.response.UserResponse;
import com.example.payheretest.security.JwtTokenProvider;
import com.example.payheretest.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signUp(@Valid @RequestBody final AuthRequest authRequest) {
        return userService.signUp(authRequest);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody final AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @SecurityRequirement(name="Bearer Authentication")
    @GetMapping("user")
    public UserResponse userInfo(HttpServletRequest request) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return userService.getByEmail(userEmail);
    }

    @SecurityRequirement(name="Bearer Authentication")
    @PatchMapping("user")
    public UserResponse updateName(HttpServletRequest request, String name) {
        String userEmail = jwtTokenProvider.getUserId(request);
        return userService.updateUserName(userEmail, name);
    }
}
