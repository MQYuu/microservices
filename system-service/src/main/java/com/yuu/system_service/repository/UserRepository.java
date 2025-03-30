package com.yuu.system_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuu.system_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
