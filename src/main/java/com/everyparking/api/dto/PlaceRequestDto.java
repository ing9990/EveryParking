package com.everyparking.api.dto;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.exception.valid.DuplicatedAddrConstraint;
import com.everyparking.exception.valid.DuplicatedPlaceNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceRequestDto {

    @DuplicatedPlaceNameConstraint
    @NotBlank(message = "장소 별칭이 빈칸입니다.")
    private String placeName;

    @NotBlank(message = "X 좌표가 빈칸입니다.")
    private String mapX;

    @NotBlank(message = "Y 좌표가 빈칸입니다.")
    private String mapY;

    @DuplicatedAddrConstraint(message = "중복된 도로명 주소입니다.")
    @NotBlank(message = "도로명 주소가 빈칸입니다.")
    private String mapAddr;

    private String message;

    private Car.CarSize size;

    @Length(max = 9999, message = "이미지 주소가 10000자리 이상입니다.")
    private String imgUrl;
}
