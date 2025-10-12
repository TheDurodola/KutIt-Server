package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.User;
import com.kutit.yrsd.data.repositories.Users;
import com.kutit.yrsd.dtos.requests.LoginUserRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.LoginUserResponse;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import static com.kutit.yrsd.utils.Mappers.map;
import static com.kutit.yrsd.utils.Validator.validate;

@Slf4j
@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    Users users;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        dataCleaner(request);
        validate(request);
        return map(users.save(map(request)));
    }

    private static void dataCleaner(RegisterUserRequest request) {
        request.setEmail(request.getEmail().toLowerCase().trim());
        request.setPassword(request.getPassword().trim());
        request.setUsername(request.getUsername().toLowerCase().trim());
        request.setFirstName(capitalizeFirstLetterOnly(request.getFirstName().trim()));
        request.setLastName(capitalizeFirstLetterOnly(request.getLastName().trim()));
    }


    private static String capitalizeFirstLetterOnly(String text) {
        text = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();

        if (text.contains("-")) {
            int index = text.indexOf("-");
            if (index + 1 < text.length()) {
                char letter = text.charAt(index + 1);
                String letterString = String.valueOf(letter).toUpperCase();
                text = text.substring(0, index + 1) + letterString + text.substring(index + 2);
            }
        }


        return text;
    }



    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        return null;
    }
}
