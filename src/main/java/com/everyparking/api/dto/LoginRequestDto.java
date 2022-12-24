package com.everyparking.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    @NotBlank(message = "이메일이 4빈칸입니다.")
    @Email(message = "이메일 포맷이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호가 빈칸입니다.")
    @Size(min = 7, max = 14, message = "7자리 이상 14자리 비밀번호가 아닙니다.")
    private String password;
}
