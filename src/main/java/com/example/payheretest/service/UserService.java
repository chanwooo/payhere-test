package com.example.payheretest.service;

import com.example.payheretest.domain.entity.User;
import com.example.payheretest.domain.request.AuthRequest;
import com.example.payheretest.domain.response.LoginResponse;
import com.example.payheretest.domain.response.UserResponse;
import com.example.payheretest.exception.InvalidAuthInfoException;
import com.example.payheretest.exception.NoSuchUserException;
import com.example.payheretest.repository.UserRepository;
import com.example.payheretest.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponse signUp(final AuthRequest authRequest) throws InvalidAuthInfoException {
        if (userRepository.findByEmail(authRequest.getId()).isPresent()) {
            log.error("Already registered user.");
            throw new InvalidAuthInfoException("Already registered user.");
        }

        String name = authRequest.getId().split("@")[0];
        String hashedPassword = BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .email(authRequest.getId())
                .hashedPassword(hashedPassword)
                .name(name)
                .build();

        return userRepository.save(user).toResponse();
    }

    @Transactional
    public LoginResponse login(final AuthRequest authRequest) throws InvalidAuthInfoException {
        User user = userRepository.findByEmail(authRequest.getId()).orElseThrow(NoSuchUserException::new);

        if (!BCrypt.checkpw(authRequest.getPassword(), user.getHashedPassword())) {
            log.error("Password is incorrect");
            throw new InvalidAuthInfoException("Password is incorrect.");
        }

        return LoginResponse.builder()
                .userId(authRequest.getId())
                .token(jwtTokenProvider.createToken(authRequest.getId()))
                .build();
    }

    @Transactional
    public UserResponse logout(final String email) {
        User user = userRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
        user.setExpiredAt(new Date().getTime());
        return user.toResponse();
    }

    @Transactional
    public User getByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
    }

    @Transactional
    public UserResponse updateUserName(final String email, final String name) {
        User user = userRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
        user.updateName(name);
        return user.toResponse();
    }

}
