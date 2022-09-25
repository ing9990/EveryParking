package com.everyparking.api.user;

/**
 * @author Taewoo
 */


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
public class UserApi {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) String name) {
        log.info("User 요청");
        if (name == null)
            return ResponseEntity.ok().body(userService.getUsers());

        return ResponseEntity.ok().body(userService.getUserByNickname(name));
    }

    @PostMapping("")
    public ResponseEntity<?> registyUsers(@Valid @RequestBody RegistryRequestDto registryDto) {
        log.info("회원가입 요청: " + registryDto.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registryUser(registryDto));
    }
}
