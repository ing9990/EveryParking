package com.everyparking.data.borrow.repository;

import com.everyparking.data.borrow.domain.BorrowHistory;
import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Taewoo
 */

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

    @Query("select b from BorrowHistory b where b.borrower = ?1")
    List<BorrowHistory> findBorrowHistoriesByBorrower(User user);

    @Query("select b from BorrowHistory b where b.rent.place.user = ?1")
    List<BorrowHistory> findBorrowHistoriesByRenter(User user);
}
