package com.example.payheretest.service;

import com.example.payheretest.domain.entity.User;
import com.example.payheretest.domain.request.AuthRequest;
import com.example.payheretest.domain.response.LoginResponse;
import com.example.payheretest.domain.response.UserResponse;
import com.example.payheretest.repository.UserRepository;
import com.example.payheretest.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    String userEmail = "test@test.test";
    String password = "pass";
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    @Test
    void signUp() {
        //given
        User user = User.builder()
                .email(userEmail)
                .hashedPassword(hashedPassword)
                .build();

        AuthRequest authRequest = AuthRequest.builder()
                .id(userEmail)
                .password(password)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        //when
        UserResponse userResponse = userService.signUp(authRequest);
        log.info("userResponse = " + userResponse);

        //then
        assertThat(userResponse.getEmail()).isEqualTo(userEmail);
    }

    @Test
    void login() {
        //given
        User user = User.builder()
                .email(userEmail)
                .hashedPassword(hashedPassword)
                .build();

        AuthRequest authRequest = AuthRequest.builder()
                .id(userEmail)
                .password(password)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtTokenProvider.createToken(anyString())).thenReturn("token");

        //when
        LoginResponse loginResponse = userService.login(authRequest);
        log.info("loginResponse = " + loginResponse);

        //then
        assertThat(loginResponse.getUserId()).isEqualTo(userEmail);
    }

    @Test
    void logout() {
        //given
        long largeExpire = 9999999999999L;
        User user = User.builder()
                .email(userEmail)
                .hashedPassword(hashedPassword)
                .expiredAt(largeExpire).build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        //when
        UserResponse response = userService.logout(userEmail);
        log.info("response = " + response);
        log.info("ExpiredAt = " + new Date(response.getExpiredAt()));

        //then
        assertThat(response.getExpiredAt()).isLessThan(largeExpire);
    }

    @Test
    void getByEmail() {
        //given
        User user = User.builder()
                .email(userEmail)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        //when
        User byEmail = userService.getByEmail(userEmail);
        log.info("byEmail = " + byEmail);

        //then
        assertThat(byEmail.getEmail()).isEqualTo(userEmail);
    }

    @Test
    void updateUserName() {
        //given
        String updatedUserName = "updated user name";
        User user = User.builder()
                .email(userEmail)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        //when
        UserResponse userResponse = userService.updateUserName(userEmail, updatedUserName);
        log.info("userResponse = " + userResponse);

        //then
        assertThat(userResponse.getName()).isEqualTo(updatedUserName);
    }
}
