package com.kutit.yrsd.data.repositories;

import com.kutit.yrsd.data.models.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Links extends MongoRepository<Link, String> {
}
