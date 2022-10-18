package com.everyparking.api.dto;

/**
 * @author Taewoo
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowRequestDto {

    @NotNull(message = "주차장을 선택해주세요.")
    private Long RentId;

    @NotNull(message = "차량을 선택해주세요.")
    private String carNumber;

    @Future(message = "시간을 정확히 선택해주세요.")
    private LocalDateTime startTime;

    @Future(message = "시간을 정확히 선택해주세요.")
    private LocalDateTime endTime;
}
