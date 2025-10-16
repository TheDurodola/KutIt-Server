package com.kutit.yrsd.data.repositories;

import com.kutit.yrsd.data.models.Entry;
import com.kutit.yrsd.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface Entries extends JpaRepository<Entry, String > {
    Optional<Entry> findByShortenedLink(String shortenedLink);
    Optional<Entry> findByOriginalLink(String originalLink);
    List<Entry> findByUpdatedAtBefore(LocalDateTime dateTime);

    List<Entry> findByUser(User user);
}
