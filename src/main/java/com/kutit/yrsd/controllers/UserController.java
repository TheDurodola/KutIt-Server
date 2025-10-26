package com.kutit.yrsd.controllers;

import com.kutit.yrsd.dtos.requests.*;
import com.kutit.yrsd.exceptions.UserNotLoggedInException;
import com.kutit.yrsd.services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @PostMapping("/entry")
    public ResponseEntity<?> createEntry(@RequestBody CreateUserEntryRequest request, HttpSession session) {
        try {
            request.setUserId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.createUserEntry(request));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/entry")
    public ResponseEntity<?> getUserEntry(@RequestBody GetUserEntryRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.getUserEntry(request));
        return new ResponseEntity<>(map, HttpStatus.FOUND);
    }

    @DeleteMapping("/entry/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String id, HttpSession session) {
        DeleteUserEntryRequest request = new DeleteUserEntryRequest();
        request.setEntryId(id);
        try {
            request.setUserId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.deleteUserEntry(request));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @PutMapping("/entry")
    public ResponseEntity<?> updateEntry(@RequestBody UpdateUserEntryRequest request, HttpSession session) {
        try {
            request.setUserId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.updateUserEntry(request));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @GetMapping("/entries")
    public ResponseEntity<?> getAllEntries(HttpSession session){
        GetAllUserEntriesRequest request = new GetAllUserEntriesRequest();
        try {
            request.setUserId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.getAllUserEntries(request));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/account")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteUserAccountRequest request, HttpSession session) {
        try {
            request.setUserId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.deleteUserAccount(request));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @PutMapping("/account")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateUserAccountRequest request, HttpSession session) {
        try {
            request.setId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.updateUserAccount(request));
        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }

    @GetMapping("/account")
    public ResponseEntity<?> getAccount(HttpSession session){
        GetUserAccountRequest request = new GetUserAccountRequest();
        try {
            request.setUserId(session.getAttribute("id").toString());
        }catch (IllegalStateException | IllegalMonitorStateException | NullPointerException e){
            throw new UserNotLoggedInException("User is not logged in. Please login first.");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", userServices.getUserAccount(request));
        return new ResponseEntity<>(map, HttpStatus.FOUND);
    }
}
