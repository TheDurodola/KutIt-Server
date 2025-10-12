package com.kutit.yrsd.dtos.responses;

import lombok.Data;

@Data
public class LoginUserResponse {
    private String email;
    private String username;
    private String lastName;
    private String firstName;
    private String fullName;
}
