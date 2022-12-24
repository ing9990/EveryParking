package com.everyparking.scheduler;

import com.everyparking.data.borrow.service.BorrowService;
import com.everyparking.data.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EndTimeScheduler {

    private final BorrowService borrowService;
    private final RentService rentService;

    @Scheduled(fixedDelay = 1000 * 10)
    public void endTimeTracker() {
        borrowService.endTime();
        rentService.endTime();
    }
}
