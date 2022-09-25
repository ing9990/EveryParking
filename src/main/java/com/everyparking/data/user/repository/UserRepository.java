package com.everyparking.data.user.repository;

import com.everyparking.data.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Taewoo
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByTel(String tel);

    boolean existsUserByNickname(String nickname);

    User findUserByNicknameContaining(String name);
}
