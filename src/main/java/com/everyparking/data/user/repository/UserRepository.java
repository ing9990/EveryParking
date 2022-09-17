package com.everyparking.data.user.repository;

import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface UserRepository extends JpaRepository<User, Long> {

}
