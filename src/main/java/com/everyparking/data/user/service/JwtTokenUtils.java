package com.everyparking.data.user.service;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.exception.InvalidAuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtils {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 10000;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    public String buildToken(String email, String nickname, String tel, String intro, long point, User.City city) {
        return "Bearer " + JWT.create().withSubject(nickname).withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)).withClaim("email", email).withClaim("nickname", nickname).withClaim("tel", tel).withClaim("intro", intro).withClaim("point", point).withClaim("city", city.toString()).sign(Algorithm.HMAC256(secret.getBytes()));
    }

    public User getUserByToken(String token) {

        if (token == null || !token.startsWith("Bearer "))
            throw new InvalidAuthenticationException();

        return userRepository
                .findUserByEmail(
                        JWT.decode(token.substring(7))
                                .getClaim("email")
                                .asString())
                .orElseThrow(InvalidAuthenticationException::new);
    }
}
