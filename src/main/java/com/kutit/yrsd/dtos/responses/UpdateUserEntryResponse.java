package com.kutit.yrsd.dtos.responses;
import lombok.Data;

@Data
public class UpdateUserEntryResponse {
    private String message;
    private String entryId;
    private String originalLink;
    private String shortenedLink;
}
