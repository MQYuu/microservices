package com.yuu.system_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuu.system_service.entity.Authority;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    List<Authority> findByUserId(Integer userId);
}
