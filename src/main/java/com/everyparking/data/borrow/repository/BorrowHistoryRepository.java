package com.everyparking.data.borrow.repository;

import com.everyparking.data.borrow.domain.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

}
