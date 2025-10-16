package com.kutit.yrsd.controllers;

import com.kutit.yrsd.dtos.requests.LoginUserRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.LoginUserResponse;
import com.kutit.yrsd.services.AuthServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthServices authServices;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Success");
        map.put("response", authServices.register(request));
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        LoginUserResponse response = authServices.login(request);
        session.setAttribute("email", response.getEmail());
        session.setAttribute("username", response.getUsername());
        session.setAttribute("id", response.getId());
        map.put("message", "Login Successful");
        map.put("response", response);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Logout Successful");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
