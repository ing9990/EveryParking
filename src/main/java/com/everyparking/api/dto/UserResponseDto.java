package com.everyparking.api.dto;

import com.everyparking.api.dto.resource.BorrowResponse;
import com.everyparking.api.dto.resource.UserBorrowResponse;
import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.car.domain.Car;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.user.domain.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName("user")
public class UserResponseDto {

    // profile
    private String nickname;
    private String email;
    private long point;
    private String introduce;
    private User.City city;
    private String tel;

    private List<Car> cars;
    private List<Place> places;

    private List<BorrowResponse> myUsing;
    private List<UserBorrowResponse> userUsing;
    private List<BorrowHistory> used;


    public static UserResponseDto of(User user, List<Car> cars, List<Place> places,
            List<BorrowResponse> myUsing, List<UserBorrowResponse> userUsing,
            List<BorrowHistory> used
    ) {

        used.sort(Comparator.comparing(BorrowHistory::getCreateAt).reversed());

        return UserResponseDto.builder().nickname(user.getNickname())
                              .email(user.getEmail()).point(user.getPoint())
                              .introduce(user.getIntroduce()).city(user.getCity()).tel(user.getTel())
                              .cars(cars).places(places)
                              .myUsing(myUsing)
                              .userUsing(userUsing)
                              .used(used)
                              .build();
    }
}
