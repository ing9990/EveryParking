package com.everyparking.data.place.repository;

import com.everyparking.data.place.domain.Place;
import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Taewoo
 */

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByAddr(String addr);
    boolean existsByName(String value);
    List<Place> findPlacesByUser(User user);
}
