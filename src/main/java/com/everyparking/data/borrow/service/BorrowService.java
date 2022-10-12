package com.everyparking.data.borrow.service;

import com.auth0.jwt.JWT;
import com.everyparking.api.dto.BorrowRequestDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.borrow.repository.BorrowRepository;
import com.everyparking.data.place.service.PlaceService;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.rent.service.GeoService;
import com.everyparking.data.rent.service.RentService;
import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.exception.RentTimeInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.data.geo.Distance;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Taewoo
 */


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final RentService rentService;
    private final PlaceService placeService;
    private final GeoService geoService;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAllBorrows() {
        var dat = borrowRepository.findAll();

        return DefaultResponseDtoEntity.of(dat.size() == 0 ? HttpStatus.NO_CONTENT : HttpStatus.OK, dat.size() == 0 ? "(성공) 대여 0건" : "(성공) 대여 " + dat.size() + "건", dat);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getRecommandAvailableParkingLots(String authorization, BorrowRequestDto borrowRequestDto) {

        Map<Integer, Long> recommandMap = new TreeMap<>();

        var user = jwtTokenUtils.getUserByToken(authorization);

        if (borrowRequestDto.getStartTime().isAfter(borrowRequestDto.getEndTime()))
            throw new RentTimeInvalidException("종료시간이 시작시간보다 이릅니다.");


        var availableLots = rentService.getAvailableLots(borrowRequestDto.getEndTime());

        List<Double> adj =
                geoService.getDistance(
                        availableLots,
                        Double.parseDouble(borrowRequestDto.getMapX()),
                        Double.parseDouble(borrowRequestDto.getMapY()));

        return null;
    }


}












