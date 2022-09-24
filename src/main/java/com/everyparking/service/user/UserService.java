package com.everyparking.service.user;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.dto.registry.RegistryRequestDto;
import com.everyparking.exception.EmailDuplicatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Object registryUser(RegistryRequestDto registryDto) {
        return userRepository.save(
                User.makeUser(registryDto.getEmail(), registryDto.getPassword(), registryDto.getNickname(), registryDto.getTel(),
                        registryDto.getIntroduce(), registryDto.getCity()));
    }


}
