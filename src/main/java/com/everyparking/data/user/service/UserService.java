package com.everyparking.data.user.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.*;
import com.everyparking.api.dto.resource.BorrowResponse;
import com.everyparking.api.dto.resource.UserBorrowResponse;
import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.borrow.repository.BorrowRepository;
import com.everyparking.data.borrow.service.BorrowHistoryService;
import com.everyparking.data.car.service.CarService;
import com.everyparking.data.place.service.PlaceService;
import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.exception.EmailNotFoundException;
import com.everyparking.exception.InvalidAuthenticationException;
import com.everyparking.exception.PasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    private final CarService carService;
    private final PlaceService placeService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    private final BorrowHistoryService borrowHistoryService;

    @Transactional
    public DefaultResponseDtoEntity registryUser(RegistryRequestDto registryDto) {
        return DefaultResponseDtoEntity.ok("회원가입 성공", userRepository.save(User.makeUser(registryDto.getEmail(), passwordEncoder.encode(registryDto.getPassword()), registryDto.getNickname(), registryDto.getTel(), registryDto.getIntroduce(), registryDto.getCity())));
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getUsers() {
        return DefaultResponseDtoEntity.ok("성공", userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity loginUser(LoginRequestDto loginRequestDto) {
        var optionalUser = userRepository.findUserByEmail(loginRequestDto.getEmail());

        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(loginRequestDto.getPassword(), optionalUser.get().getPassword())) {
                var user = optionalUser.get();
                var token = jwtTokenUtils.buildToken(user.getEmail(), user.getNickname(), user.getTel(), user.getIntroduce(), user.getPoint(), user.getCity());

                return DefaultResponseDtoEntity.ok("로그인 성공", token);
            }
            throw new PasswordNotMatchException();
        }
        throw new EmailNotFoundException();
    }

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getUserById(String authorization) {

        var user = jwtTokenUtils.getUserByToken(authorization);
        var cars = carService.findCarsByUserId(user);
        var places = placeService.findPlacesByUser(user);


        List<BorrowResponse> myUsing = new ArrayList<>();
        List<UserBorrowResponse> userUsing = new ArrayList<>();
        List<BorrowHistory> used = borrowHistoryService.findAllHistories();

        borrowRepository.findBorrowsByBorrowerIs(user).forEach(dat -> myUsing.add(BorrowResponse.of(dat)));
        borrowRepository.findBorrowsByRenter(user).forEach(dat -> userUsing.add(UserBorrowResponse.of(dat)));

        return DefaultResponseDtoEntity.ok("성공", UserResponseDto.of(user, cars, places, myUsing, userUsing, used));
    }

    /**
     * @param renter 주차장 등록자
     * @param user   주차장 대여자
     * @param cost   렌트 비용
     */

    @Transactional
    public void payPoint(User renter, User user, long cost) {
        renter.setPoint(renter.getPoint() + cost);
        user.setPoint(user.getPoint() - cost);

        userRepository.save(renter);
        userRepository.save(user);
    }


    @Transactional
    public DefaultResponseDtoEntity addPoint(String authorizaiton) {
        var user = jwtTokenUtils.getUserByToken(authorizaiton);
        user.setPoint(user.getPoint() + 10000);
        userRepository.save(user);

        return DefaultResponseDtoEntity.ok("10000 포인트 충전 완료.", user);
    }

    @Transactional
    public DefaultResponseDtoEntity editUser(String authorization, EditUserDto editRequestDto) {
        var user = jwtTokenUtils.getUserByToken(authorization);

        user.setIntroduce(editRequestDto.getIntro());
        user.setCity(editRequestDto.getCity());
        user.setTel(editRequestDto.getTel());
        user.setUpdated(LocalDateTime.now());

        return DefaultResponseDtoEntity.ok("정보 수정이 완료.", userRepository.save(user));
    }


    public User findUserById(Long user) {
        return userRepository.findById(user)
                             .orElseThrow(InvalidAuthenticationException::new);
    }
}
