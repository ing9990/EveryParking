package com.everyparking.data.place.repository;

import com.everyparking.data.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByAddr(String addr);

    boolean existsByName(String value);
}
