package com.everyparking.api.user;

/**
 * @author Taewoo
 */


import com.everyparking.dto.registry.RegistryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {

    @PostMapping("")
    public ResponseEntity<?> registyUser(@Valid @RequestBody RegistryRequestDto registryDto) {

    }
}
