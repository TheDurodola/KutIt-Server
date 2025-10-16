package com.kutit.yrsd.dtos.requests;
import lombok.Data;

@Data
public class DeleteUserAccountRequest {
    private String userId;
    private String password;
}
