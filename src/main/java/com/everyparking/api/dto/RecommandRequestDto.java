package com.everyparking.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommandRequestDto {

    @NotBlank(message = "현재 X 좌표가 빈 칸입니다.")
    private String mapX;

    @NotBlank(message = "현재 Y 좌표가 빈 칸입니다.")
    private String mapY;

    @NotBlank(message = "자동차를 선택해주세요.")
    private String carNumber;

    @Future(message = "시간이 올바르지 않습니다.")
    @NotNull(message = "시작 시간이 빈칸입니다.")
    private LocalDateTime startTime;

    @Future(message = "시간이 올바르지 않습니다.")
    @NotNull(message = "종료 시간이 빈칸입니다.")
    private LocalDateTime endTime;

}











