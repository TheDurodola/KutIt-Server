package com.kutit.yrsd.dtos.requests;
import lombok.Data;

@Data
public class DeleteUserEntryRequest {
    private String userId;
    private String entryId;
}
