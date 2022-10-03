package com.everyparking.dto.place;

/**
 * @author Taewoo
 */


import java.util.*;

import com.everyparking.data.place.service.valid.DuplicatedAddrConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceRequestDto {
    private String placeName;

    private double mapX;
    private double mapY;

    @DuplicatedAddrConstraint
    private String mapAddr;

    private String message;
}
