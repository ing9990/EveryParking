package com.everyparking.exception;

/**
 * @author Taewoo
 */


public class PlaceNotFoundException extends RuntimeException {
    public PlaceNotFoundException() {
        super("대여장소를 찾을 수 없습니다.");
    }

}
