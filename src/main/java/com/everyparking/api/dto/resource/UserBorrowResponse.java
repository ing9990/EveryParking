package com.everyparking.api.dto.resource;

import com.everyparking.data.borrow.domain.Borrow;
import com.everyparking.data.car.domain.Car;
import com.everyparking.data.place.domain.Place;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName("userUsing")
public class UserBorrowResponse {

    private String placeImg;
    private String placeAddr;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String message;

    private String borrowerName;
    private String borrowerTel;

    private String carNumber;
    private String carModel;
    private Car.CarSize carSize;

    private String placeName;
    private Place.PlaceStatus placeStatus;

    public static UserBorrowResponse of(Borrow borrow) {

        var rent = borrow.getRent();
        var place = rent.getPlace();
        var car = borrow.getCar();


        return UserBorrowResponse.builder()
                                 .placeAddr(place.getAddr())
                                 .placeImg(place.getImgUrl())
                                 .startAt(borrow.getStartAt())
                                 .endAt(borrow.getEndAt())
                                 .message(rent.getMessage())
                                 .carNumber(car.getCarNumber())
                                 .carSize(car.getCarSize())
                                 .carModel(car.getCarModel())
                                 .placeStatus(place.getPlaceStatus())
                                 .placeName(place.getName())
                                 .borrowerName(borrow.getBorrower().getNickname())
                                 .borrowerTel(borrow.getBorrower().getTel())
                                 .build();
    }
}
