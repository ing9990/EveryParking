package com.everyparking.exception;

/**
 * @author Taewoo
 */


public class RentTimeInvalidException extends RuntimeException {
    public RentTimeInvalidException() {
        super("대여시간이 정확하지 않습니다.");
    }

    public RentTimeInvalidException(String message) {
        super(message);
    }
}
