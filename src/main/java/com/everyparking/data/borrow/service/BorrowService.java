package com.everyparking.data.borrow.service;

import com.everyparking.api.dto.*;
import com.everyparking.data.borrow.domain.Borrow;
import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.borrow.repository.BorrowHistoryRepository;
import com.everyparking.data.borrow.repository.BorrowRepository;
import com.everyparking.data.car.service.CarService;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.service.PlaceService;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.rent.service.GeoService;
import com.everyparking.data.rent.service.RentService;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.data.user.service.UserService;
import com.everyparking.exception.BeShortOfPointException;
import com.everyparking.exception.RentTimeInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
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
    private final BorrowHistoryRepository borrowHistoryRepository;

    private final RentService rentService;
    private final GeoService geoService;
    private final CarService carService;
    private final UserService userService;
    private final PlaceService placeService;

    private final JwtTokenUtils jwtTokenUtils;

    @Value("${recommend.default-score}")
    private Integer DEFAULT_SCORE;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAllBorrows(Pageable pageable) {
        var dat = borrowRepository.findAll(pageable).getContent();

        return dat.size() == 0 ?
                DefaultResponseDtoEntity.of(HttpStatus.NO_CONTENT, "NO CONTENT")
                : DefaultResponseDtoEntity.ok("조회 완료", dat);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getRecommendAvailableParkingLots(String authorization,
                                                                     RecommandRequestDto recommandRequestDto) {

        if (recommandRequestDto.getStartTime().isAfter(recommandRequestDto.getEndTime()))
            throw new RentTimeInvalidException("종료시간이 시작시간보다 이릅니다.");


        Map<Long, Integer> recommendMap = new LinkedHashMap<>();
        List<RecommendResponseDto> list = new ArrayList<>();

        var user = jwtTokenUtils.getUserByToken(authorization);
        var car = carService.getCarByCarNumber(recommandRequestDto.getCarNumber());

        var availableLots =
                rentService.
                        getAvailableLots(recommandRequestDto.getEndTime(), user.getId())
                        .stream().filter(item ->
                                item.getPlace().getPlaceSize()
                                        .getValue() >= car.getCarSize().getValue())
                        .collect(Collectors.toList());

        var adj = geoService.getDistance(availableLots, Double.parseDouble(recommandRequestDto.getMapX()), Double.parseDouble(recommandRequestDto.getMapY()));

        if (availableLots.size() != 0) {
            availableLots.forEach(x -> recommendMap.put(x.getId(), DEFAULT_SCORE));
            for (int i = 0; i < adj.size(); i++) {

                var item = availableLots.get(i);
                var itemId = item.getId();
                var dist = adj.get(i);

                recommendMap.put(itemId, recommendMap.get(itemId) - (int) Math.floor(dist));

                if (!item.getStart().isAfter(recommandRequestDto.getStartTime())) {
                    var itemStart = item.getStart();
                    var meStart = recommandRequestDto.getStartTime();
                    var rst = Duration.between(itemStart, meStart).toHours();

                    recommendMap.put(itemId, recommendMap.get(itemId) - (int) rst);
                }
                list.add(RecommendResponseDto.of(item, dist, recommendMap.get(itemId)));
            }
            list.sort(Comparator.comparing(RecommendResponseDto::getRecommendScore).reversed());
            return DefaultResponseDtoEntity.of(HttpStatus.OK, "성공", list);
        }

        var rents = rentService.findRentsByNotUserId(user.getId());
        var adjDist = geoService.getDistance(rents, Double.parseDouble(recommandRequestDto.getMapX()), Double.parseDouble(recommandRequestDto.getMapY()));

        for (int i = 0; i < adjDist.size(); i++) {
            var item = rents.get(i);
            var dist = adjDist.get(i);

            list.add(RecommendResponseDto.of(item, dist));
        }

        list.sort(Comparator.comparing(RecommendResponseDto::getDist));

        return DefaultResponseDtoEntity.of(HttpStatus.OK, "추천 주차장이 없어 거리순서로 조회합니다.", list);
    }


    @Transactional
    public DefaultResponseDtoEntity borrow(String authorization, BorrowRequestDto borrowRequestDto) {

        var user = jwtTokenUtils.getUserByToken(authorization);
        var car = carService.getCarByCarNumber(borrowRequestDto.getCarNumber());
        var rent = rentService.findRentById(borrowRequestDto.getRentId());

        borrowRequestDto.setStartTime(borrowRequestDto.getStartTime().plusHours(3));

        var borrowTime = Math.abs(rentService.compareEndTime(borrowRequestDto.getStartTime(), borrowRequestDto.getEndTime()).toHours());
        var cost = borrowTime * rent.getCost();

        if (borrowRequestDto.getStartTime().isAfter(borrowRequestDto.getEndTime()))
            throw new RentTimeInvalidException("종료시간이 시작시간보다 이릅니다.");

        if (rentService.comparePoint(borrowTime * rent.getCost(), user.getPoint()))
            throw new BeShortOfPointException("포인트가 부족합니다.");


        rentService.updateStatus(rent);
        placeService.updateStatus(rent.getPlace(), Place.PlaceStatus.inUse);

        var borrow = borrowRepository.save(
                Borrow.builder()
                        .borrower(user)
                        .rent(rent)
                        .startAt(borrowRequestDto.getStartTime())
                        .endAt(borrowRequestDto.getEndTime()).car(car).build());

        log.info("주차 요금: " + cost);
        log.info("주차 시작까지 남은 시간: " + Math.abs(rentService.compareEndTime(rent.getStart(), borrow.getStartAt()).toHours()));

        userService.payPoint(rent.getPlace().getUser(), user, cost);

        return DefaultResponseDtoEntity.of(HttpStatus.CREATED, "주차장 대여 성공",
                BorrowResponseDto.of(borrow, borrow.getStartAt(), user, car, rent, cost));
    }


    public void endTime() {
        var borrows = borrowRepository.findBorrowsByEndAtIsBefore(LocalDateTime.now());

        for (Borrow borrow : borrows) {
            var rent = borrow.getRent();
            var renter = rent.getPlace().getUser();
            var place = rent.getPlace();
            var car = borrow.getCar();
            var borrower = borrow.getBorrower();

            rentService.updateStatus(rent, Rent.RentStatus.waiting);
            placeService.updateBorrow(rent.getPlace(), Place.PlaceStatus.waiting);
            borrowRepository.deleteById(borrow.getId());

            var history = BorrowHistory.builder()
                    .renterName(renter.getNickname())
                    .renterTel(renter.getTel())
                    .carNumber(car.getCarNumber())
                    .carModel(car.getCarModel())
                    .borrowerName(borrower.getNickname())
                    .message(rent.getMessage())
                    .cost(rent.getCost())
                    .addr(place.getAddr())
                    .placeImgUrl(place.getImgUrl())
                    .endAt(borrow.getEndAt())
                    .startAt(borrow.getStartAt())
                    .createAt(LocalDateTime.now())
                    .build();

            borrowHistoryRepository.save(history);
        }
    }

    public List<Borrow> findBorrowsByBorrower(User user) {
        return borrowRepository.findBorrowsByRenter(user);
    }

}












