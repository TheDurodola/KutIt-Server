package com.kutit.yrsd.data.repositories;

import com.kutit.yrsd.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends MongoRepository<User, String> {
}
