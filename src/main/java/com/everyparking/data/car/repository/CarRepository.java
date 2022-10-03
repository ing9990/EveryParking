package com.everyparking.data.car.repository;

import com.everyparking.data.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface CarRepository extends JpaRepository<Car, Long> {
 boolean existsByCarNumber(String carNumber);
}
