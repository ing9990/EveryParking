package com.everyparking.api.dto;

/**
 * @author Taewoo
 */


import com.everyparking.data.place.domain.Place;
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
}
