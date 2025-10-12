package com.kutit.yrsd.utils;

import com.kutit.yrsd.data.models.Entry;
import com.kutit.yrsd.data.models.User;
import com.kutit.yrsd.dtos.requests.CreateEntryRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.CreateEntryResponse;
import com.kutit.yrsd.dtos.responses.LoginUserResponse;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;


import static com.kutit.yrsd.utils.ShortLinkGenerator.generateShortCode;

public class Mappers {
    public static Entry map(CreateEntryRequest request) {
        Entry entry = new Entry();
        entry.setOriginalLink(request.getOriginalLink());
        entry.setShortenedLink(generateShortCode());
        return entry;
    }

    public static CreateEntryResponse map(Entry entry) {
        CreateEntryResponse response = new CreateEntryResponse();
        response.setShortenedLink(entry.getShortenedLink());
        return response;
    }

    public static RegisterUserResponse map(User user) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFirstname() + " " + user.getLastname());
        return response;
    }


    public static LoginUserResponse mapUserToLoginUserResponse(User user) {
        LoginUserResponse response = new LoginUserResponse();
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFirstname() + " " + user.getLastname());
        response.setFirstName(user.getFirstname());
        response.setLastName(user.getLastname());
        return response;
    }


    public static User map(RegisterUserRequest register) {
        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(register.getPassword());
        user.setEmail(register.getEmail());
        user.setFirstname(register.getFirstName());
        user.setLastname(register.getLastName());
        return user;
    }
}
