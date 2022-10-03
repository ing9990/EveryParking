package com.everyparking.api.dto;

/**
 * @author Taewoo
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultResponseDtoEntity {
    private String message;

    private Object data;

    private HttpStatus httpStatus;

    public static DefaultResponseDtoEntity of(HttpStatus httpStatus, String message) {
        return DefaultResponseDtoEntity
                .builder()
                .httpStatus(httpStatus)
                .message(message)
                .build();
    }

    public static DefaultResponseDtoEntity of(HttpStatus httpStatus, String message, Object data) {
        return DefaultResponseDtoEntity
                .builder()
                .httpStatus(httpStatus)
                .message(message)
                .data(data)
                .build();
    }

    public static DefaultResponseDtoEntity ok(String message) {
        return DefaultResponseDtoEntity
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();
    }

    public static DefaultResponseDtoEntity ok(String message, Object data) {
        return DefaultResponseDtoEntity
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();
    }
}
