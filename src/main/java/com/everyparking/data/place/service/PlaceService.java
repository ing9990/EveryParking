package com.everyparking.data.place.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.api.dto.PlaceRequestDto;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.repository.PlaceRepository;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.exception.PlaceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public DefaultResponseDtoEntity addPlace(String token, PlaceRequestDto dto) {
        var user = jwtTokenUtils.getUserByToken(token);

        if (placeRepository.existsByAddr(dto.getMapAddr() + ":" + dto.getMessage())) {
            throw new DuplicateKeyException("장소가 중복되었습니다.");
        }

        var data = placeRepository.save(Place.dtoToEntity(user, dto.getPlaceName(), dto.getMapAddr() + ":" + dto.getMessage(), dto.getMapX(), dto.getMapY(), dto.getSize(), dto.getImgUrl()));

        return DefaultResponseDtoEntity.of(HttpStatus.CREATED, "주차공간 등록 성공", data);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAllPlace() {
        return DefaultResponseDtoEntity.ok("성공", placeRepository.findAll());
    }

    public void updateBorrow(Place place, Place.PlaceStatus placeStatus) {
        place.setPlaceStatus(placeStatus);
        placeRepository.save(place);
    }

    public List<Place> findPlacesByUser(User user) {
        return placeRepository.findPlacesByUser(user);
    }

    public Optional<Place> findById(Long placeId) {
        return placeRepository.findById(placeId);
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }


    public Place updateStatus(Long placeId) {
        var place = placeRepository.findById(placeId).orElseThrow(PlaceNotFoundException::new);

        place.setPlaceStatus(Place.PlaceStatus.waiting);
        return placeRepository.save(place);
    }

    public void updateStatus(Place place, Place.PlaceStatus placeStatus) {
        place.setPlaceStatus(placeStatus);
    }

}
