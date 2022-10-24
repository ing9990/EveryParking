package com.everyparking.data.rent.repository;


import com.everyparking.data.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("select r " + "from Rent  r " + "where r.end >= ?1 and r.place.placeStatus <> 'inUse' and r.place.user.id <> ?2 " + "order by r.start")
    List<Rent> getRecommandLists(LocalDateTime end, Long userId);

    @Query("select r " + "from Rent r " + "where r.place.user.id <> ?1 and r.rentStatus = 'pending'")
    List<Rent> findRentsByNotUserId(Long id);

    @Transactional
    @Modifying
    @Query("delete from Rent r where r.place.id = ?1")
    void deleteRentByPlace(Long placeId);

    @Query("select r from Rent r where r.end > ?1")
    List<Rent> findRentsByEndIsAfter(LocalDateTime now);
}




