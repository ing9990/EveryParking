package com.everyparking.api.dto.resource;


import com.everyparking.data.borrow.domain.Borrow;
import com.everyparking.data.car.domain.Car;
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
@JsonRootName("myUsing")
public class BorrowResponse {

    private Long borrowId;
    private String placeImg;
    private String placeAddr;

    private LocalDateTime borrowStartAt;
    private LocalDateTime borrowEndAt;
    private LocalDateTime rentStartAt;
    private LocalDateTime rentEndAt;

    private String message;

    private long cost;

    private String renterName;
    private String renterTel;

    private String carNumber;
    private String carModel;
    private Car.CarSize carSize;

    public static BorrowResponse of(Borrow borrow) {

        var rent = borrow.getRent();
        var place = rent.getPlace();
        var owner = place.getUser();
        var car = borrow.getCar();

        return BorrowResponse.builder().placeAddr(place.getAddr())
                             .borrowId(borrow.getId())
                             .carModel(car.getCarModel())
                             .placeImg(place.getImgUrl())
                             .borrowStartAt(borrow.getStartAt())
                             .cost(rent.getCost())
                             .borrowEndAt(borrow.getEndAt())
                             .rentStartAt(rent.getStart())
                             .rentEndAt(rent.getEnd())
                             .message(rent.getMessage())
                             .renterName(owner.getNickname())
                             .renterTel(owner.getTel())
                             .carNumber(car.getCarNumber())
                             .carSize(car.getCarSize())
                             .build();
    }
}
