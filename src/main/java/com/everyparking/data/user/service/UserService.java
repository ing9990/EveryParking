package com.everyparking.data.user.service;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.data.user.service.user.JwtTokenUtils;
import com.everyparking.dto.login.LoginRequestDto;
import com.everyparking.dto.login.LoginResponseDto;
import com.everyparking.dto.registry.RegistryRequestDto;
import com.everyparking.exception.EmailNotFoundException;
import com.everyparking.exception.PasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    public Object registryUser(RegistryRequestDto registryDto) {
        return userRepository.save(User.makeUser(registryDto.getEmail(), passwordEncoder.encode(registryDto.getPassword()), registryDto.getNickname(), registryDto.getTel(), registryDto.getIntroduce(), registryDto.getCity()));
    }

    public Object getUsers() {
        return userRepository.findAll();
    }

    public Object getUserByNickname(String name) {
        return userRepository.findUserByNicknameContaining(name);
    }

    public Object loginUser(LoginRequestDto loginRequestDto) {
        var optionalUser = userRepository.findUserByEmail(loginRequestDto.getEmail());

        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(loginRequestDto.getPassword(), optionalUser.get().getPassword())) {
                var user = optionalUser.get();
                var token = jwtTokenUtils.
                        buildToken(user.getEmail(), user.getNickname(), user.getTel(), user.getIntroduce(), user.getPoint(), user.getCity());

                return LoginResponseDto.builder().token(token).build();
            }
            throw new PasswordNotMatchException();
        }

        throw new EmailNotFoundException();
    }

    public Object getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            var user = userRepository.findById(id).get();
            return user;
        }
        return null;
    }
}
