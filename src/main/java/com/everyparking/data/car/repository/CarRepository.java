package com.everyparking.data.car.repository;

import com.everyparking.data.car.domain.Car;
import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, String> {

    boolean existsByCarNumber(String carNumber);

    @Query("select c from Car c where c.user = ?1")
    List<Car> findCarsByUser(User user);

    @Query("select c from Car c where c.carNumber = ?1")
    Optional<Car> findCarByCarNumber(String carNumber);
}
