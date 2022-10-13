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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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

    @Value("${recommand.default-score}")
    private Integer DEFAULT_SCORE;

    @Value("${recommand.default-meter-score}")
    private Integer MINUS_SCORE;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAllBorrows() {
        var dat = borrowRepository.findAll();

        return DefaultResponseDtoEntity.of(dat.size() == 0 ? HttpStatus.NO_CONTENT : HttpStatus.OK, dat.size() == 0 ? "(성공) 대여 0건" : "(성공) 대여 " + dat.size() + "건", dat);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getRecommandAvailableParkingLots(String authorization, BorrowRequestDto borrowRequestDto) {

        Map<Long, Integer> recommandMap = new HashMap<>();

        var user = jwtTokenUtils.getUserByToken(authorization);

        if (borrowRequestDto.getStartTime().isAfter(borrowRequestDto.getEndTime()))
            throw new RentTimeInvalidException("종료시간이 시작시간보다 이릅니다.");


        var availableLots = rentService.getAvailableLots(borrowRequestDto.getEndTime());

        List<Double> adj = geoService.getDistance(availableLots, Double.parseDouble(borrowRequestDto.getMapX()), Double.parseDouble(borrowRequestDto.getMapY()));

        availableLots.forEach(x -> recommandMap.put(x.getId(), DEFAULT_SCORE));

        for (int i = 0; i < adj.size(); i++) {
            var item = availableLots.get(i);
            var itemId = item.getId();

            var dist = adj.get(i);

            recommandMap.put(itemId, recommandMap.get(itemId) - (int) Math.floor(dist));

            if (!item.getStart().isAfter(borrowRequestDto.getStartTime())) {
                var itemStart = item.getStart();
                var meStart = borrowRequestDto.getStartTime();
                var rst = Duration.between(itemStart, meStart).toHours();

                recommandMap.put(itemId, recommandMap.get(itemId) - (int) rst);
            }
        }
        List<Rent> list = new ArrayList<>();

        recommandMap.keySet()
                .forEach(x -> list.add(rentService.getRentById(x)));

        return DefaultResponseDtoEntity.of(HttpStatus.OK, "성공", list);
    }


}












