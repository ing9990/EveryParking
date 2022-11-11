package com.everyparking.api.borrow;

import com.everyparking.api.dto.BorrowRequestDto;
import com.everyparking.api.dto.RecommandRequestDto;
import com.everyparking.data.borrow.service.BorrowService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Taewoo
 */


@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowApi {

    private final BorrowService borrowService;

    @GetMapping
    public ResponseEntity<?> getAllBorrows() {
        return ResponseEntity.status(HttpStatus.OK).body(borrowService.getAllBorrows());
    }

    @PostMapping
    public ResponseEntity<?> addBorrow(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization, @Valid @RequestBody BorrowRequestDto borrowRequestDto) {
        var res = borrowService.borrow(authorization, borrowRequestDto);

        return ResponseEntity.status(res.getHttpStatus()).body(res);
    }

    @PostMapping("/recommand")
    public ResponseEntity<?> getRecommandAvailableParkingLots(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
            @NotBlank(message = "현재 X 좌표가 빈 칸입니다.") @RequestParam(value = "mapY", required = true) String mapY,

            @NotBlank(message = "현재 Y 좌표가 빈 칸입니다.") @RequestParam(value = "mapX", required = true) String mapX,

            @NotBlank(message = "자동차를 선택해주세요.") @RequestParam(value = "carNumber", required = true) String carNumber,

            @Future(message = "시간이 올바르지 않습니다.") @NotNull(message = "시작 시간이 빈칸입니다.") @RequestParam(value = "startTime", required = true) LocalDateTime startTime,

            @Future(message = "시간이 올바르지 않습니다.") @NotNull(message = "종료 시간이 빈칸입니다.") @RequestParam(value = "endTime", required = true) LocalDateTime endTime) {
        var res = borrowService.getRecommendAvailableParkingLots(authorization, RecommandRequestDto.builder().mapX(mapX).mapY(mapY).carNumber(carNumber).startTime(startTime).endTime(endTime).build());

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}












