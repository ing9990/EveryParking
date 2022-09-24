package com.everyparking.data.user.repository;

import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Taewoo
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    boolean existsUserByEmail(String email);
    boolean existsUserByTel(String tel);
    boolean existsUserByNickname(String nickname);
}
