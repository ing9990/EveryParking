package com.everyparking.data.borrow.service;

import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.borrow.repository.BorrowHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BorrowHistoryService {

    private final BorrowHistoryRepository borrowHistoryRepository;

    public List<BorrowHistory> findAllHistories() {
        return borrowHistoryRepository.findAll();
    }
}












