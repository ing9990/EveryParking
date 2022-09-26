package com.everyparking.api.users;

/**
 * @author Taewoo
 */


import com.everyparking.dto.login.LoginRequestDto;
import com.everyparking.dto.registry.RegistryRequestDto;
import com.everyparking.data.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsersApi {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok()
                .body(userService.getUsers());
    }

    @PostMapping("")
    public ResponseEntity<?> registyUser(@Valid @RequestBody RegistryRequestDto registryDto) {
        log.info("회원가입 요청: " + registryDto.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registryUser(registryDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("로그인 요청: " + loginRequestDto.getEmail());

        return ResponseEntity.ok()
                .body(userService.loginUser(loginRequestDto));
    }
}
