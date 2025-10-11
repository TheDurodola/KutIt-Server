package com.kutit.yrsd.data.repositories;

import com.kutit.yrsd.data.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Entries extends JpaRepository<Entry,String> {
    Entry findByShortened(String shortened);
}
