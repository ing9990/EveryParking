package com.everyparking.api.users;


import com.everyparking.api.dto.EditUserDto;
import com.everyparking.api.dto.LoginRequestDto;
import com.everyparking.api.dto.RegistryRequestDto;
import com.everyparking.data.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/me")
    public ResponseEntity<?> getUserById(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization
    ) {
        return ResponseEntity.ok()
                             .body(userService.getUserById(authorization));
    }

    @PatchMapping("")
    public ResponseEntity<?> addPoint(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizaiton
    ) {
        return ResponseEntity.ok()
                             .body(userService.addPoint(authorizaiton));
    }

    @PutMapping("")
    public ResponseEntity<?> edit(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
            @RequestBody EditUserDto editRequestDto
    ) {
        return ResponseEntity.ok()
                             .body(userService.editUser(authorization, editRequestDto));
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
