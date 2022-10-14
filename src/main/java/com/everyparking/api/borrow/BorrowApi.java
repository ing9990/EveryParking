package com.everyparking.api.borrow;

import com.everyparking.api.dto.BorrowRequestDto;
import com.everyparking.data.borrow.service.BorrowService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Taewoo
 */


@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/borrow")
public class BorrowApi {

    private final BorrowService borrowService;

    @GetMapping
    public ResponseEntity<?> getAllBorrows() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(borrowService.getAllBorrows());
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommandAvailableParkingLots(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization,
            @Valid @RequestBody BorrowRequestDto borrowRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(borrowService.getRecommandAvailableParkingLots(authorization, borrowRequestDto));
    }

}












