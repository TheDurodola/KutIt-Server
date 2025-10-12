package com.kutit.yrsd.dtos.requests;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String identifier;
    private String password;
}
