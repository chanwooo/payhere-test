package com.example.payheretest.security;

import com.example.payheretest.domain.entity.User;
import com.example.payheretest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    UserRepository userRepository;

    @Mock
    HttpServletRequest httpServletRequest;

    JwtTokenProvider jwtTokenProvider;

    final String secretKey = "fd1a7e9e-73fe-11ed-87eb-7ae1364e0a63";
    final long aliveDurationMilli = 3600000L;
    final String testUserEmail = "testuser@payhere.in";
    final String sampleToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlckBwYXloZXJlLmluIiwiaWF0IjoxNjcwNDc4NjUyLCJleHAiOjE2NzA0ODIyNTJ9.AMttLIxr1xgZ4Q4UY-15Jj5K3O_E_nW1F969BBEdFvs";

    @BeforeEach
    void before() {
        jwtTokenProvider = new JwtTokenProvider(secretKey, aliveDurationMilli, userRepository);
    }

    @Test
    void createToken() {
        //given
        User user = User.builder().email(testUserEmail).build();
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));

        //when
        String token = jwtTokenProvider.createToken(testUserEmail);
        log.info("token = " + token);

        //then
        assertThat(token.length()).isEqualTo(sampleToken.length());
    }

    @Test
    void resolveToken() {
        //given
        when(httpServletRequest.getHeader("Authorization"))
                .thenReturn("Bearer "+sampleToken);

        //when
        String resolveToken = jwtTokenProvider.resolveToken(httpServletRequest);
        log.info("resolveToken = " + resolveToken);

        //then
        assertThat(resolveToken).isEqualTo(sampleToken);
    }

    @Test
    void getUserIdByToken() {
        //given
        //when
        String userId = jwtTokenProvider.getUserId(sampleToken);
        log.info("userId = " + userId);
        //then
        assertThat(userId).isEqualTo(testUserEmail);
    }

    @Test
    void getUserIdByHttpRequest() {
        //given
        User user = User.builder().email(testUserEmail).build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        String token = jwtTokenProvider.createToken(testUserEmail);
        when(httpServletRequest.getHeader("Authorization"))
                .thenReturn("Bearer "+token);

        //when
        String userId = jwtTokenProvider.getUserId(httpServletRequest);
        log.info("userId = " + userId);

        //then
        assertThat(userId).isEqualTo(testUserEmail);
    }

    @Test
    void validateToken() {
        //given
        long largeExpire = 9999999999999L;
        final User user = User.builder()
                .email(testUserEmail)
                .expiredAt(largeExpire)
                .build();

        final String token = jwtTokenProvider.createToken(testUserEmail);
        log.info("token = " + token);

        when(userRepository.findByEmail(testUserEmail)).thenReturn(Optional.of(user));

        //when
        boolean validateToken = jwtTokenProvider.validateToken(token);
        log.info("validateToken = " + validateToken);

        //then
        Assertions.assertThat(validateToken).isTrue();
    }
}
