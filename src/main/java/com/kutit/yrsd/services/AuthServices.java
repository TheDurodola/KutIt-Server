package com.kutit.yrsd.services;

import com.kutit.yrsd.dtos.requests.LoginUserRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.LoginUserResponse;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;

public interface AuthServices {
    RegisterUserResponse register(RegisterUserRequest register);
    LoginUserResponse login(LoginUserRequest request);
}
