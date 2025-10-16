package com.kutit.yrsd.dtos.responses;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String id;
    private String fullName;
    private String email;
    private String username;
}
