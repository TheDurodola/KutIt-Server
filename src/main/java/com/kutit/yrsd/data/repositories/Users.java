package com.kutit.yrsd.data.repositories;

import com.kutit.yrsd.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Users extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
