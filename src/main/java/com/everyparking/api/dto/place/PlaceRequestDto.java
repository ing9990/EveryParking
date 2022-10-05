package com.everyparking.api.dto.place;

/**
 * @author Taewoo
 */


import java.util.*;

import com.everyparking.data.place.service.valid.DuplicatedAddrConstraint;
import com.everyparking.data.place.service.valid.DuplicatedPlaceNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceRequestDto {

    @DuplicatedPlaceNameConstraint
    @NotBlank(message = "장소이름이 빈칸입니다.")
    private String placeName;

    @NotBlank(message = "X 좌표가 빈칸입니다.")
    private double mapX;

    @NotBlank(message = "Y 좌표가 빈칸입니다.")
    private double mapY;

    @DuplicatedAddrConstraint(message = "중복된 도로명 주소입니다.")
    @NotBlank(message = "도로명 주소가 빈칸입니다.")
    private String mapAddr;

    private String message;
}
