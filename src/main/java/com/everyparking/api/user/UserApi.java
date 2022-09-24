package com.everyparking.api.user;

/**
 * @author Taewoo
 */


import com.everyparking.dto.registry.RegistryRequestDto;
import com.everyparking.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> registyUser(@Valid @RequestBody RegistryRequestDto registryDto) {
        log.info("회원가입 요청: " + registryDto.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registryUser(registryDto));
    }
}
