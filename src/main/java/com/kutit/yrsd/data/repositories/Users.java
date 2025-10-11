package com.kutit.yrsd.data.repositories;

import com.kutit.yrsd.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Users extends JpaRepository<User,String> {
    User findByUsername(String username);
}
