package com.everyparking.exception.global;

import com.everyparking.exception.*;
import com.everyparking.exception.dto.Error;
import com.everyparking.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Taewoo
 */


@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler({InvalidAuthenticationException.class, RentTimeInvalidException.class, BeShortOfPointException.class, PlaceNotFoundException.class})
    public ResponseEntity<?> custom(HttpServletRequest req, Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder().path(req.getRequestURI()).message(e.getMessage()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgsNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<Error> errorList = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            var field = (FieldError) error;
            var fieldName = field.getField();
            var message = field.getDefaultMessage();

            errorList.add(Error.builder().field(fieldName).message(message).path(request.getRequestURI()).build());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorList(errorList).message("").requestUrl(request.getRequestURI()).statusCode(HttpStatus.BAD_REQUEST.value()).resultCode("FAIL").build());
    }

    @ExceptionHandler({EmailNotFoundException.class, PasswordNotMatchException.class})
    public ResponseEntity<?> emailException(HttpServletRequest request, Exception e) {
        Exception exception = null;

        if (e instanceof EmailNotFoundException) exception = (EmailNotFoundException) e;
        if (e instanceof PasswordNotMatchException) exception = (PasswordNotMatchException) e;

        log.warn(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Error.builder().message(exception.getMessage()).path(request.getRequestURI()).build());
    }

}
