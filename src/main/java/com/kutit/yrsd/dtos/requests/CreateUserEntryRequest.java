package com.kutit.yrsd.dtos.requests;

import lombok.Data;

@Data
public class CreateUserEntryRequest {
    private String originalLink;
    private String userId;
}
