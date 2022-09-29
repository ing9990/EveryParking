package com.everyparking.exception;

/**
 * @author Taewoo
 */


import java.util.*;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("Invalid jwt token");
    }
}
