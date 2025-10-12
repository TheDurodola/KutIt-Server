package com.kutit.yrsd.services;


import com.kutit.yrsd.dtos.requests.CreateEntryRequest;
import com.kutit.yrsd.dtos.requests.GetEntryRequest;
import com.kutit.yrsd.dtos.responses.CreateEntryResponse;
import com.kutit.yrsd.dtos.responses.GetEntryResponse;

public interface EntryServices {
    CreateEntryResponse createdEntry(CreateEntryRequest request);
    GetEntryResponse getEntry(GetEntryRequest request);
}
