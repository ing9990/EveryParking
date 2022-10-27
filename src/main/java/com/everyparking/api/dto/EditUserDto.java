package com.everyparking.api.dto;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserDto {
    private String tel;
    private String intro;
    private User.City city;
}
