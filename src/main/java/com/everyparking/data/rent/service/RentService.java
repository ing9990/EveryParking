package com.everyparking.data.rent.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.AddRentDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.service.PlaceService;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.rent.repository.RentRepository;
import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.exception.PlaceNotFoundException;
import com.everyparking.exception.RentTimeInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RentService {

    private final RentRepository rentRepository;

    private final PlaceService placeService;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getMyPlace(String authorization) {
        var user = jwtTokenUtils.getUserByToken(authorization);
        List<Place> places = placeService.findPlacesByUser(user);
        return DefaultResponseDtoEntity.ok("성공", places);
    }

    @Transactional
    public DefaultResponseDtoEntity addRent(String authorization, AddRentDto addRentDto) {

        var place = placeService.findById(addRentDto.getPlaceId()).orElseThrow(PlaceNotFoundException::new);

        var user = jwtTokenUtils.getUserByToken(authorization);

        if (LocalDateTime.now().isAfter(addRentDto.getStartTime()) || LocalDateTime.now().isAfter(addRentDto.getEndTime()) || addRentDto.getStartTime().isAfter(addRentDto.getEndTime()))
            throw new RentTimeInvalidException("대여 시간이 정확하지 않습니다.");

        var rent = Rent.dtoToEntity(place, addRentDto.getCost(), addRentDto.getMessage(), Rent.RentStatus.waiting, addRentDto.getStartTime().minusHours(3).plusSeconds(49), addRentDto.getEndTime().minusHours(3).plusSeconds(49));

        placeService.updateBorrow(place, Place.PlaceStatus.pending);

        return DefaultResponseDtoEntity.of(HttpStatus.CREATED, "렌트 등록 성공", rentRepository.save(rent));
    }


    @Transactional(readOnly = true)
    public List<Rent> getAvailableLots(LocalDateTime endTime, Long userId) {
        return rentRepository.getRecommandLists(endTime, userId);
    }

    @Transactional(readOnly = true)
    public Rent getRentById(Long x) {
        return rentRepository.findById(x).get();
    }

    @Transactional(readOnly = true)
    public List<Rent> getAllRents() {
        return rentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Rent> findRentsByNotUserId(Long id) {
        return rentRepository.findRentsByNotUserId(id);
    }

    @Transactional
    public DefaultResponseDtoEntity cancelRent(String authorization, Long placeId) {
        var user = jwtTokenUtils.getUserByToken(authorization);
        var place = placeService.updateStatus(placeId);

        rentRepository.deleteRentByPlaceId(placeId);

        return DefaultResponseDtoEntity.ok("취소되었습니다. [" + place.getName() + "]");
    }

    public Rent findRentById(Long rentId) {
        return rentRepository.findById(rentId).orElseThrow(PlaceNotFoundException::new);
    }

    public boolean comparePoint(long cost, long point) {
        return cost > point;
    }

    @Transactional
    @Modifying
    public void updateStatus(Rent rent) {
        rent.setRentStatus(Rent.RentStatus.inUse);
        rentRepository.save(rent);
    }

    @Transactional
    @Modifying
    public void updateStatus(Rent rent, Rent.RentStatus status) {
        rent.setRentStatus(status);
        rentRepository.save(rent);
    }

    public Duration compareEndTime(LocalDateTime endTime, Rent rent) {
        return Duration.between(endTime, rent.getEnd());
    }

    public Duration compareEndTime(LocalDateTime start, LocalDateTime endTime) {
        return Duration.between(endTime, start);
    }


    @Transactional
    @Modifying
    public void updateStartTime(Rent rent, LocalDateTime endAt) {
        rent.setStart(endAt);
        rentRepository.save(rent);
    }

    @Transactional
    @Modifying
    public void endTime() {
        var rents = rentRepository.findRentsByEndIsAfter(LocalDateTime.now());

        for (Rent rent : rents) {
            rent.setRentStatus(Rent.RentStatus.waiting);
            rentRepository.save(rent);
        }
    }
}
