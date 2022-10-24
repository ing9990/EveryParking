package com.everyparking.data.borrow.service;

import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.borrow.repository.BorrowHistoryRepository;
import com.everyparking.data.user.domain.User;
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


    public List<BorrowHistory> findBorrowsByUser(User user) {
        return borrowHistoryRepository.findBorrowHistoriesByBorrower(user.getNickname());
    }

    public List<BorrowHistory> findBorrowHistoriesByUser(User user) {
        return borrowHistoryRepository.findBorrowHistoriesByRenter(user.getNickname());
    }

    public List<BorrowHistory> findAllHistories() {
        return borrowHistoryRepository.findAll();
    }
}












