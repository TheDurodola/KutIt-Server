package com.kutit.yrsd.controllers;

import com.kutit.yrsd.dtos.requests.CreateEntryRequest;
import com.kutit.yrsd.dtos.requests.GetEntryRequest;
import com.kutit.yrsd.services.EntryServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController

public class EntryController {

    @Autowired
    private EntryServicesImpl entryServices;


    @PostMapping("/entry")
    public ResponseEntity<?> createEntry(@RequestBody CreateEntryRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", entryServices.createdEntry(request));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{shortenedLink}")
    public ResponseEntity<?> getOriginalLink(@PathVariable String shortenedLink) {
        GetEntryRequest request = new GetEntryRequest();
        request.setShortendLink(shortenedLink);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", entryServices.getEntry(request));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

