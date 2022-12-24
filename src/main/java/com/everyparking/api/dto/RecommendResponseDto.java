package com.everyparking.api.dto;


import com.everyparking.data.rent.domain.Rent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendResponseDto {

    private Long rentId;
    private int recommendScore;

    private String placeOwnerName;
    private String placeImgUrl;
    private String placeName;
    private String placeAddr;

    private String mapX;
    private String mapY;
    private double dist;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private long cost;
    private String message;

    public static RecommendResponseDto of(Rent rent, double dist) {
        var place = rent.getPlace();
        var user = place.getUser();

        return RecommendResponseDto
                .builder()
                .rentId(rent.getId())
                .placeOwnerName(user.getNickname())
                .placeImgUrl(place.getImgUrl())
                .placeName(place.getName())
                .placeAddr(place.getAddr())
                .mapX(place.getMapX())
                .mapY(place.getMapY())
                .dist(dist)
                .startTime(rent.getStart())
                .endTime(rent.getEnd())
                .cost(rent.getCost())
                .message(rent.getMessage())
                .build();
    }

    public static RecommendResponseDto of(Rent rent, double dist, int recommendScore) {
        var place = rent.getPlace();
        var user = place.getUser();

        return RecommendResponseDto
                .builder()
                .rentId(rent.getId())
                .recommendScore(recommendScore)
                .placeOwnerName(user.getNickname())
                .placeImgUrl(place.getImgUrl())
                .placeName(place.getName())
                .placeAddr(place.getAddr())
                .mapX(place.getMapX())
                .mapY(place.getMapY())
                .dist(dist)
                .startTime(rent.getStart())
                .endTime(rent.getEnd())
                .cost(rent.getCost())
                .message(rent.getMessage())
                .build();
    }
}
