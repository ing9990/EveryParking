package com.everyparking.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("Invalid jwt token");
    }
}
