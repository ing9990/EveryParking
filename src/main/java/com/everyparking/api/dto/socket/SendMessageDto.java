package com.everyparking.api.dto.socket;

/**
 * @author Taewoo
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendMessageDto {
    private String authorization;
    private Long user;
    private String message;
}
