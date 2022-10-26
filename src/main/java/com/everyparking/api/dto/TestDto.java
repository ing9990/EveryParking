package com.everyparking.api.dto;

/**
 * @author Taewoo
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {

    private String id;
    private String username;
    private String introduce;
}
