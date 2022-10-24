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

    @Value("${recommend.default-meter-score}")
    private Integer DEFAULT_METER_SCORE;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getAllBorrows() {
        var dat = borrowRepository.findAll();

        return DefaultResponseDtoEntity.of(dat.size() == 0 ? HttpStatus.NO_CONTENT : HttpStatus.OK, dat.size() == 0 ? "(성공) 대여 0건" : "(성공) 대여 " + dat.size() + "건", dat);
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getRecommendAvailableParkingLots(String authorization, RecommendRequestDto recommendRequestDto) {

        if (recommendRequestDto.getStartTime().isAfter(recommendRequestDto.getEndTime()))
            throw new RentTimeInvalidException("종료시간이 시작시간보다 이릅니다.");


        Map<Long, Integer> recommendMap = new LinkedHashMap<>();
        List<RecommendResponseDto> list = new ArrayList<>();

        var user = jwtTokenUtils.getUserByToken(authorization);
        var car = carService.getCarByCarNumber(recommendRequestDto.getCarNumber());
        var availableLots = rentService.getAvailableLots(recommendRequestDto.getEndTime(), user.getId()).stream().filter(item -> item.getPlace().getPlaceSize().getValue() >= car.getCarSize().getValue()).collect(Collectors.toList());
        var adj = geoService.getDistance(availableLots, Double.parseDouble(recommendRequestDto.getMapX()), Double.parseDouble(recommendRequestDto.getMapY()));

        if (availableLots.size() != 0) {
            availableLots.forEach(x -> recommendMap.put(x.getId(), DEFAULT_SCORE));
            for (int i = 0; i < adj.size(); i++) {

                var item = availableLots.get(i);
                var itemId = item.getId();
                var dist = adj.get(i);

                recommendMap.put(itemId, recommendMap.get(itemId) - (int) Math.floor(dist));

                if (!item.getStart().isAfter(recommendRequestDto.getStartTime())) {
                    var itemStart = item.getStart();
                    var meStart = recommendRequestDto.getStartTime();
                    var rst = Duration.between(itemStart, meStart).toHours();

                    recommendMap.put(itemId, recommendMap.get(itemId) - (int) rst);
                }
                list.add(RecommendResponseDto.of(item, dist, recommendMap.get(itemId)));
            }
            list.sort(Comparator.comparing(RecommendResponseDto::getRecommendScore).reversed());
            return DefaultResponseDtoEntity.of(HttpStatus.OK, "성공", list);
        }

        var rents = rentService.findRentsByNotUserId(user.getId());
        var adjDist = geoService.getDistance(rents, Double.parseDouble(recommendRequestDto.getMapX()), Double.parseDouble(recommendRequestDto.getMapY()));

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

        var borrow = borrowRepository.save(Borrow.builder().borrower(user).rent(rent).startAt(borrowRequestDto.getStartTime()).endAt(borrowRequestDto.getEndTime()).car(car).build());

        log.info("주차 요금: " + cost);
        log.info("주차 시작까지 남은 시간: " + Math.abs(rentService.compareEndTime(rent.getStart(), borrow.getStartAt()).toHours()));

        if (Math.abs(rentService.compareEndTime(borrow.getEndAt(), rent).toHours()) >= 1) {
            log.info(Math.abs(rentService.compareEndTime(borrow.getEndAt(), rent).toHours()) + " 시간이 남습니다.");
            rentService.updateStartTime(rent, borrow.getEndAt());
        }

        userService.payPoint(rent.getPlace().getUser(), user, cost);

        var dat = BorrowResponseDto.of(borrow, borrow.getStartAt(), user, car, rent, cost);
        return DefaultResponseDtoEntity.of(HttpStatus.CREATED, "주차장 대여 성공", dat);
    }

    public List<Borrow> findBorrowsByUser(User user) {
        return borrowRepository.findBorrowByBorrower(user);
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
                                       .carModel(car.getCarModel()).borrowerName(borrower.getNickname()).message(rent.getMessage()).cost(rent.getCost()).addr(place.getAddr()).placeImgUrl(place.getImgUrl()).endAt(rent.getEnd()).startAt(rent.getStart()).createAt(LocalDateTime.now()).build();

            borrowHistoryRepository.save(history);
        }
    }

    public List<Borrow> findBorrowsByBorrower(User user) {
        return borrowRepository.findBorrowsByRenter(user);
    }
}












