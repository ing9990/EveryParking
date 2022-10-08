package com.everyparking.data.rent.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.AddRentDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.repository.PlaceRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RentService {

    private final PlaceRepository placeRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getMyPlace(String authorization) {

        var user = jwtTokenUtils.getUserByToken(authorization);

        List<Place> places = placeRepository.findPlacesByUser(user);

        return DefaultResponseDtoEntity.ok("성공", places);
    }

    @Transactional
    public DefaultResponseDtoEntity addRent(String authorization, AddRentDto addRentDto) {
        var user = jwtTokenUtils.getUserByToken(authorization);

        return DefaultResponseDtoEntity.ok("d");
    }
}
