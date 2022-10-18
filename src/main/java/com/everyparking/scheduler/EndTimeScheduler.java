package com.everyparking.scheduler;

import com.everyparking.data.borrow.service.BorrowService;
import jdk.jfr.consumer.RecordedFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Taewoo
 */


@Component
@Slf4j
@RequiredArgsConstructor
public class EndTimeScheduler {

    private final BorrowService borrowService;

    @Scheduled(fixedDelay = 10000)
    public void endTimeTracker() {

    }
}
