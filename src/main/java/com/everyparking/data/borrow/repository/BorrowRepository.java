package com.everyparking.data.borrow.repository;

import com.everyparking.data.borrow.domain.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface BorrowRepository extends JpaRepository<Borrow,Long> {
}
