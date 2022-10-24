package com.everyparking.data.borrow.repository;

import com.everyparking.data.borrow.domain.Borrow;
import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    @Query("select b from Borrow b where b.borrower = ?1")
    List<Borrow> findBorrowByBorrower(User user);

    @Query("select b from Borrow b where b.endAt < ?1")
    List<Borrow> findBorrowsByEndAtIsBefore(LocalDateTime now);



    @Query("select b from Borrow b where b.borrower = ?1")
    List<Borrow> findBorrowsByBorrowerIs(User user);

    @Query("select b from Borrow b where b.rent.place.user = ?1")
    List<Borrow> findBorrowsByRenter(User user);

}
