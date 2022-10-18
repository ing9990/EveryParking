package com.everyparking.exception;

/**
 * @author Taewoo
 */


public class BeShortOfPointException extends RuntimeException {
    public BeShortOfPointException() {
        super();
    }

    public BeShortOfPointException(String message) {
        super(message);
    }
}
