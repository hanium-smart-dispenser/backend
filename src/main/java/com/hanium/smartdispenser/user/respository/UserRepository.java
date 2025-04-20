package com.hanium.smartdispenser.user.respository;

import com.hanium.smartdispenser.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(@Param("email") String email);
}
