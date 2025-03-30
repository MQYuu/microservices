package com.example.restaurant.entity;

import com.example.restaurant.validation.ValidField;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ValidField(minLength = 3, maxLength = 50, requireLetters = true, requireNumbers = false, allowNumbers = false)
    @Column(nullable = false, unique = true)
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotEmpty(message = "Email không được để trống")
    @ValidField(minLength = 8, maxLength = 100, requireLetters = true, requireNumbers = false, allowNumbers = true)
    @Column(nullable = false, unique = true)
    private String email;

    @ValidField(minLength = 8, maxLength = 100, requireLetters = true, requireNumbers = true, allowNumbers = true)
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Authority> authorities;

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Authority> getAuthorities() { return authorities; }
    public void setAuthorities(Set<Authority> authorities) { this.authorities = authorities; }
}
