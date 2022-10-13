package com.everyparking.data.rent.repository;


import com.everyparking.data.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Taewoo
 */

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> getRentsByStartGreaterThanEqualOrderByStartAsc(LocalDateTime start);


    @Query("select r from Rent r where r.end >= ?1 and r.isBorrowed = false and r.place.user.id <> ?2 order by r.start")
    List<Rent> getRecommandLists(LocalDateTime end, Long userId);
}




