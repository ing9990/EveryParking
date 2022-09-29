package com.everyparking.exception.global;

import com.everyparking.exception.EmailNotFoundException;
import com.everyparking.exception.InvalidAuthenticationException;
import com.everyparking.exception.PasswordNotMatchException;
import com.everyparking.exception.dto.Error;
import com.everyparking.exception.dto.ErrorResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Taewoo
 */


@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<?> invalidAuth() {
        return ResponseEntity.badRequest()
                .body("Invalid Auth");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgsNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<Error> errorList = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            var field = (FieldError) error;
            var fieldName = field.getField();
            var message = field.getDefaultMessage();

            errorList.add(Error.builder().field(fieldName).message(message).build());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorList(errorList).message("").requestUrl(request.getRequestURI()).statusCode(HttpStatus.BAD_REQUEST.value()).resultCode("FAIL").build());
    }
}
