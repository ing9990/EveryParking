package com.everyparking.data.user.service;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.dto.registry.RegistryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Object registryUser(RegistryRequestDto registryDto) {
        return userRepository.save(
                User.makeUser(registryDto.getEmail(), passwordEncoder.encode(registryDto.getPassword()), registryDto.getNickname(), registryDto.getTel(),
                        registryDto.getIntroduce(), registryDto.getCity()));
    }

    public Object getUsers() {
        return userRepository.findAll();
    }

    public Object getUserByNickname(String name) {
        return userRepository.findUserByNicknameContaining(name);
    }


}
