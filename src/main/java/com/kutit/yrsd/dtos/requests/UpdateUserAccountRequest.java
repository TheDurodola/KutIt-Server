package com.kutit.yrsd.dtos.requests;
import lombok.Data;

@Data
public class UpdateUserAccountRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String username;
}
