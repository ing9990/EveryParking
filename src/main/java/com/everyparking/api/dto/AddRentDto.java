package com.everyparking.api.dto;

/**
 * @author Taewoo
 */


import java.time.LocalDateTime;
import java.util.*;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRentDto {

    @NotNull(message = "장소는 필수 값입니다.")
    private Long placeId;

    @Future(message = "시작 시간이 유효하지 않습니다.")
    @NotNull(message = "시작 시간은 필수 값입니다.")
    private LocalDateTime startTime;

    @Future(message = "종료 시간이 유효하지 않습니다.")
    @NotNull(message = "종료 시간은 필수 값입니다.")
    private LocalDateTime endTime;

    @Length(max = 255, message = "주의사항은 255자리 미만으로 작성해주세요.")
    private String message;

    private long cost;

}
