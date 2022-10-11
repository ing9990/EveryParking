package com.everyparking.data.rent.repository;


import com.everyparking.data.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Taewoo
 */

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> getRentsByStartGreaterThanEqualOrderByStartAsc(LocalDateTime start);
}




