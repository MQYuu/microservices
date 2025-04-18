package com.yuu.system_service.service;

import org.springframework.stereotype.Service;

import com.yuu.system_service.entity.Authority;
import com.yuu.system_service.repository.AuthorityRepository;

import java.util.List;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll(); 
    }

    public List<Authority> getAuthoritiesByUserId(Integer userId) {
        return authorityRepository.findByUserId(userId);
    }

    public Authority addAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    public Authority updateAuthority(Integer id, String newRole) {
        Authority authority = authorityRepository.findById(id).orElse(null);
        if (authority != null) {
            authority.setRole(newRole);
            return authorityRepository.save(authority);
        }
        return null;
    }    
    
    public Authority getAuthorityById(Integer id) {
        return authorityRepository.findById(id).orElse(null);
    }    

    public void removeAuthority(Integer id) {
        if (authorityRepository.existsById(id)) {
            authorityRepository.deleteById(id);
        }
    }
    
}
