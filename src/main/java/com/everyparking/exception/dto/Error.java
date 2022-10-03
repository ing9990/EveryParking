package com.everyparking.exception.dto;

/**
 * @author Taewoo
 */


import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    private String field;
    private String message;
    private String path;
}
