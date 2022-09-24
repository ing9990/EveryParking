package com.everyparking.dto.registry;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.service.user.DuplicatedEmailConstraint;
import com.everyparking.data.user.service.user.DuplicatedNicknameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistryRequestDto {

    @Email(message = "이메일 포맷과 다릅니다.")
    @NotBlank(message = "이메일은 필수 값입니다.")
    @DuplicatedEmailConstraint
    private String email;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Size(min = 7, max = 20, message = "비밀번호를 7자 이상 20자 미만으로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수 값입니다.")
    @DuplicatedNicknameConstraint
    private String nickname;

    @NotBlank(message = "전화번호는 필수 값입니다..")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String tel;

    private String introduce;

    private User.City city;
}
