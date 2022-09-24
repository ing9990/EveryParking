package com.everyparking.dto.registry;

/**
 * @author Taewoo
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistryRequestDto {
    private String username;
    private String email;
    private String password;
}
