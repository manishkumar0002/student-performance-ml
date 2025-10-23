package com.studentperformance.repository;

import com.studentperformance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // For login authentication
    Optional<User> findByUsername(String username);

    // For registration validation
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
