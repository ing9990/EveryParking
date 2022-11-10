package com.everyparking.api.dto;

/**
 * @author Taewoo
 */

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class DefaultResponseDtoEntity {

    public enum Swal {
        USE, USELESS
    }

    private String message;
    private Object data;
    private HttpStatus httpStatus;
    private Swal swal;


    public static DefaultResponseDtoEntity of(HttpStatus httpStatus, String message) {
        return DefaultResponseDtoEntity.builder().httpStatus(httpStatus).message(message).build();
    }

    public static DefaultResponseDtoEntity of(HttpStatus httpStatus, String message, Object data, Swal swal) {
        return DefaultResponseDtoEntity.builder().httpStatus(httpStatus).message(message)
                                       .swal(swal)
                                       .data(data).build();
    }

    public static DefaultResponseDtoEntity ok(String message, Swal swal) {
        return DefaultResponseDtoEntity.builder().httpStatus(HttpStatus.OK).message(message).swal(swal).build();
    }

    public static DefaultResponseDtoEntity ok(String message, Object data, Swal swal) {
        return DefaultResponseDtoEntity.builder().httpStatus(HttpStatus.OK)
                                       .swal(swal)
                                       .message(message).data(data).build();
    }

    public static DefaultResponseDtoEntity ok(String message, Object[] data, Swal swal) {
        return DefaultResponseDtoEntity.builder().httpStatus(HttpStatus.OK).message(message)
                                       .swal(swal).data(data).build();
    }
}
