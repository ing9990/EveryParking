package com.everyparking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Taewoo
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException() {
        super();
    }

    public CarNotFoundException(String message) {
        super("자동차 번호 " + message + "가 존재하지 않습니다.");
    }
}
