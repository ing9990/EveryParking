package com.everyparking.data.user.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.api.dto.LoginRequestDto;
import com.everyparking.api.dto.RegistryRequestDto;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import com.everyparking.exception.EmailNotFoundException;
import com.everyparking.exception.PasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    public DefaultResponseDtoEntity registryUser(RegistryRequestDto registryDto) {
        return DefaultResponseDtoEntity.ok("회원가입 성공", userRepository.save(User.makeUser(registryDto.getEmail(), passwordEncoder.encode(registryDto.getPassword()), registryDto.getNickname(), registryDto.getTel(), registryDto.getIntroduce(), registryDto.getCity())));
    }

    public DefaultResponseDtoEntity getUsers() {
        return DefaultResponseDtoEntity.ok("성공", userRepository.findAll());
    }

    public DefaultResponseDtoEntity loginUser(LoginRequestDto loginRequestDto) {
        var optionalUser = userRepository.findUserByEmail(loginRequestDto.getEmail());

        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(loginRequestDto.getPassword(), optionalUser.get().getPassword())) {
                var user = optionalUser.get();
                var token = jwtTokenUtils.buildToken(user.getEmail(), user.getNickname(), user.getTel(), user.getIntroduce(), user.getPoint(), user.getCity());

                return DefaultResponseDtoEntity.ok("로그인 성공", token);
            }
            throw new PasswordNotMatchException();
        }
        throw new EmailNotFoundException();
    }

    public DefaultResponseDtoEntity getUserById(Long id) {
        if (userRepository.findById(id).isPresent())
            return DefaultResponseDtoEntity.ok("성공", userRepository.findById(id).get());

        return DefaultResponseDtoEntity.of(HttpStatus.NO_CONTENT, "성공", "");
    }
}
