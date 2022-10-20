package com.everyparking.api.dto;

/**
 * @author Taewoo
 */

import com.everyparking.data.borrow.domain.Borrow;
import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.car.domain.Car;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.user.domain.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName("user")
public class UserResponseDto {

    private String nickname;
    private String email;
    private long point;
    private String introduce;
    private User.City city;
    private String tel;

    private List<Car> cars;
    private List<Place> places;
    private List<Borrow> myBorrows;
    private List<Borrow> userBorrows;

    private List<BorrowHistory> myBorrowHistories;
    private List<BorrowHistory> userBorrowHistories;

    public static UserResponseDto of(User user, List<Car> cars, List<Place> places, List<Borrow> myBorrows, List<Borrow> userBorrows, List<BorrowHistory> myBorrowHistories, List<BorrowHistory> userBorrowHistories) {
        return UserResponseDto.builder().nickname(user.getNickname()).email(user.getEmail()).point(user.getPoint()).introduce(user.getIntroduce()).city(user.getCity()).tel(user.getTel()).cars(cars).places(places).myBorrows(myBorrows).myBorrowHistories(myBorrowHistories).userBorrows(userBorrows).userBorrowHistories(userBorrowHistories).build();
    }

    public static UserResponseDto of(User user, List<Car> cars, List<Place> places) {
        return UserResponseDto.builder().nickname(user.getNickname()).email(user.getEmail()).point(user.getPoint()).introduce(user.getIntroduce()).city(user.getCity()).tel(user.getTel()).cars(cars).places(places).build();
    }
}
