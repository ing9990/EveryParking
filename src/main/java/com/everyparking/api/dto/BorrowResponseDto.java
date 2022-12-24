package com.everyparking.api.dto;


import com.everyparking.data.borrow.domain.Borrow;
import com.everyparking.data.car.domain.Car;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowResponseDto {

    private Long parkingId;
    private LocalDateTime startTime;

    private String mapX;
    private String mapY;

    private String addr;

    private String renterName;
    private User borrower;

    private long cost;


    public static BorrowResponseDto of(Borrow savedBorrow, LocalDateTime startAt, User user, Car car, Rent rent, long cost) {
        return BorrowResponseDto
                .builder()
                .parkingId(savedBorrow.getId())
                .startTime(startAt)
                .mapX(rent.getPlace().getMapX())
                .mapY(rent.getPlace().getMapY())
                .addr(rent.getPlace().getAddr())
                .renterName(rent.getPlace().getName())
                .borrower(user)
                .cost(cost)
                .build();
    }
}
