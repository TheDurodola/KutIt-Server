package com.kutit.yrsd.dtos.responses;
import lombok.Data;

@Data
public class CreateUserEntryResponse {
    private String id;
    private String shortenedLink;
    private String createdAt;
}
