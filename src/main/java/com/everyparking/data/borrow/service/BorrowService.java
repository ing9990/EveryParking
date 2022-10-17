package com.everyparking.data.borrow.service;

import com.everyparking.api.dto.BorrowRequestDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.api.dto.RecommendResponseDto;
import com.everyparking.data.borrow.repository.BorrowRepository;
import com.everyparking.data.car.service.CarService;
import com.everyparking.data.rent.service.GeoService;
import com.everyparking.data.rent.service.RentService;
import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.exception.RentTimeInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

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
    private final GeoService geoService;
    private final JwtTokenUtils jwtTokenUtils;
    private final CarService carService;

    @Value("${recommend.default-score}")
    private Integer DEFAULT_SCORE;

    @Value("${recommend.default-meter-score}")

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAllBorrows() {
        var dat = borrowRepository.findAll();

        return DefaultResponseDtoEntity.of(dat.size() == 0 ? HttpStatus.NO_CONTENT : HttpStatus.OK, dat.size() == 0 ? "(성공) 대여 0건" : "(성공) 대여 " + dat.size() + "건", dat);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getRecommendAvailableParkingLots(String authorization, BorrowRequestDto borrowRequestDto) {

        Map<Long, Integer> recommendMap = new LinkedHashMap<>();

        var user = jwtTokenUtils.getUserByToken(authorization);
        var car = carService.getCarByCarNumber(borrowRequestDto.getCarNumber());

        var availableLots = rentService.getAvailableLots(borrowRequestDto.getEndTime(), user.getId())
                .stream()
                .filter(item -> item.getPlace().getPlaceSize().getValue() >= car.getCarSize().getValue())
                .collect(Collectors.toList());

        var adj = geoService.getDistance(availableLots, Double.parseDouble(borrowRequestDto.getMapX()), Double.parseDouble(borrowRequestDto.getMapY()));
        List<RecommendResponseDto> list = new ArrayList<>();

        if (borrowRequestDto.getStartTime().isAfter(borrowRequestDto.getEndTime()))
            throw new RentTimeInvalidException("종료시간이 시작시간보다 이릅니다.");

        availableLots.forEach(x -> recommendMap.put(x.getId(), DEFAULT_SCORE));

        for (int i = 0; i < adj.size(); i++) {
            var item = availableLots.get(i);
            var itemId = item.getId();
            var dist = adj.get(i);

            recommendMap.put(itemId, recommendMap.get(itemId) - (int) Math.floor(dist));

            if (!item.getStart().isAfter(borrowRequestDto.getStartTime())) {
                var itemStart = item.getStart();
                var meStart = borrowRequestDto.getStartTime();
                var rst = Duration.between(itemStart, meStart).toHours();

                recommendMap.put(itemId, recommendMap.get(itemId) - (int) rst);

                recommendMap
                        .keySet()
                        .forEach(it -> {
                            log.info("추천된 장소: " + rentService.getRentById(it).getPlace().getName() + "\t " +
                                    "점수: " + recommendMap.get(it));
                        });
            }

            list.add(RecommendResponseDto.builder()
                    .placeOwnerName(item.getPlace().getUser().getNickname())
                    .placeName(item.getPlace().getName())
                    .placeAddr(item.getPlace().getAddr())
                    .placeImgUrl(item.getPlace().getImgUrl())
                    .mapX(item.getPlace().getMapX())
                    .mapY(item.getPlace().getMapY())
                    .dist(dist)
                    .startTime(item.getStart())
                    .endTime(item.getEnd())
                    .cost(item.getCost())
                    .message(item.getMessage())
                    .build());
        }

        if (list.size() == 0)
            return DefaultResponseDtoEntity.of(HttpStatus.NO_CONTENT, "추천 주차장이 없습니다.", List.of());

        return DefaultResponseDtoEntity.of(HttpStatus.OK, "성공", list);
    }


    public DefaultResponseDtoEntity borrow(String authorization, BorrowRequestDto borrowRequestDto) {

        var user = jwtTokenUtils.getUserByToken(authorization);


        return null;
    }
}












