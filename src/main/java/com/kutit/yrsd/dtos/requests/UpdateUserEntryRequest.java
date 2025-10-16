package com.kutit.yrsd.dtos.requests;
import lombok.Data;

@Data
public class UpdateUserEntryRequest {
    private String userId;
    private String entryId;
    private String originalLink;
}
