package com.example.restaurant.repository;

import com.example.restaurant.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    List<Authority> findByUserId(Integer userId);
}
