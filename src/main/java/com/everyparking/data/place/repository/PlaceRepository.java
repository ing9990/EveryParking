package com.everyparking.data.place.repository;

import com.everyparking.data.place.domain.Place;
import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Taewoo
 */

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("select (count(p) > 0) from Place p where p.addr = ?1")
    boolean existsByAddr(String addr);

    @Query("select p from Place p where p.user = ?1")
    List<Place> findPlacesByUser(User user);

    @Query("select (count(p) > 0) from Place p where p.name = ?1")
    boolean existsByName(String value);
}
