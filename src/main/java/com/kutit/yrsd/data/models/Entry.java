package com.kutit.yrsd.data.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "entries")
@EntityListeners(AuditingEntityListener.class)
public class Entry {

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    @Id
    private String id;

    @Column(name = "original_link", nullable = false, updatable = true)
    private String originalLink;

    @Column(name = "shortened_link", nullable = false)
    private String shortenedLink;

    @Column(name = "click", nullable = false)
    private long click;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
