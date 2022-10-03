package com.everyparking.data.user.service.valid;

/**
 * @author Taewoo
 */


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.exception.InvalidAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 10000;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    public String buildToken(String email, String nickname, String tel, String intro, long point, User.City city) {
        return JWT.create()
                .withSubject(nickname)
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .withClaim("email", email)
                .withClaim("nickname", nickname)
                .withClaim("tel", tel)
                .withClaim("intro", intro)
                .withClaim("point", point)
                .withClaim("city", city.toString())
                .sign(Algorithm.HMAC256(secret.getBytes()));
    }

    public User getUserByToken(String token) {
        return userRepository.findUserByEmail(JWT.decode(token).getClaim("email").asString())
                .orElseThrow(() -> new InvalidAuthenticationException());
    }
}
