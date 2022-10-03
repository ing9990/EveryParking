package com.everyparking.data.place.service;

/**
 * @author Taewoo
 */


import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.repository.PlaceRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import com.everyparking.dto.place.PlaceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final JwtTokenUtils jwtTokenUtils;

    public Place addPlace(String token, PlaceRequestDto dto) {
        var user = jwtTokenUtils.getUserByToken(token);

        return placeRepository
                .save(Place.dtoToEntity(user,
                        dto.getPlaceName(),
                        dto.getMapAddr(),
                        dto.getMessage(),
                        dto.getMapX(),
                        dto.getMapY()));
    }

    public List<Place> getAllPlace() {
        return placeRepository.findAll();
    }
}
