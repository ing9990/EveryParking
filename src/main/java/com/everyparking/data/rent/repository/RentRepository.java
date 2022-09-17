package com.everyparking.data.rent.repository;


import com.everyparking.data.rent.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface RentRepository extends JpaRepository<Rent, Long> {

}




