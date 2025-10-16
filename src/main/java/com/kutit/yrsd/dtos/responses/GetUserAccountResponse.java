package com.kutit.yrsd.dtos.responses;
import lombok.Data;

@Data
public class GetUserAccountResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}
