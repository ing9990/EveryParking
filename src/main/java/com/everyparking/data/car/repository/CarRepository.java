package com.everyparking.data.car.repository;

import com.everyparking.data.car.domain.Car;
import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Taewoo
 */

public interface CarRepository extends JpaRepository<Car, Long> {
 boolean existsByCarNumber(String carNumber);
 List<Car> findCarsByUser(User user);
}
