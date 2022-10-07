package com.everyparking.data.place.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.repository.PlaceRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import com.everyparking.api.dto.place.PlaceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public DefaultResponseDtoEntity addPlace(String token, PlaceRequestDto dto) {
        var user = jwtTokenUtils.getUserByToken(token);

        var data = placeRepository
                .save(Place.dtoToEntity(user,
                        dto.getPlaceName(),
                        dto.getMapAddr(),
                        dto.getMessage(),
                        dto.getMapX(),
                        dto.getMapY(),
                        dto.getImgUrl()));





        return DefaultResponseDtoEntity
                .of(HttpStatus.CREATED, "주차공간 등록 성공", data);
    }

    public DefaultResponseDtoEntity getAllPlace() {
        return DefaultResponseDtoEntity
                .ok("성공", placeRepository.findAll());
    }
}
