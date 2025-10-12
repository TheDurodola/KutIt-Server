package com.kutit.yrsd.dtos.responses;

import lombok.Data;

@Data
public class GetEntryResponse {
    private String originalLink;
    private long clicks;
}
