package com.kutit.yrsd.dtos.responses;
import lombok.Data;

@Data
public class GetUserEntryResponse {
    private String id;
    private String originalLink;
    private String shortenedLink;
    private String createdAt;
    private String updatedAt;
    private long click;
}
