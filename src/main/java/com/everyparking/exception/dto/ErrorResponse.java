package com.everyparking.exception.dto;

/**
 * @author Taewoo
 */


import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    int statusCode;
    String requestUrl;
    String code;
    String message;
    String resultCode;

    List<Error> errorList;
}
