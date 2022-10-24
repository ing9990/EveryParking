package com.everyparking.data.borrow.repository;

import com.everyparking.data.borrow.domain.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

    @Query("select b from BorrowHistory b where b.borrowerName = ?1")
    List<BorrowHistory> findBorrowHistoriesByBorrower(String borrowerName);

    @Query("select b from BorrowHistory b where b.renterName = ?1")
    List<BorrowHistory> findBorrowHistoriesByRenter(String renterName);
}
