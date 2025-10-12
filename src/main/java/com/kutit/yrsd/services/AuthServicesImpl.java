package com.kutit.yrsd.services;

import com.kutit.yrsd.data.models.User;
import com.kutit.yrsd.data.repositories.Users;
import com.kutit.yrsd.dtos.requests.LoginUserRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.responses.LoginUserResponse;
import com.kutit.yrsd.dtos.responses.RegisterUserResponse;
import com.kutit.yrsd.exceptions.EmailAlreadyExistsException;
import com.kutit.yrsd.exceptions.UserDoesntExistException;
import com.kutit.yrsd.exceptions.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import static com.kutit.yrsd.utils.Mappers.map;
import static com.kutit.yrsd.utils.Mappers.mapUserToLoginUserResponse;
import static com.kutit.yrsd.utils.PasswordHasher.hashPassword;
import static com.kutit.yrsd.utils.PasswordHasher.matches;
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
        request.setPassword(hashPassword(request.getPassword()));
        if(users.findByEmail(request.getEmail()).isPresent()) throw new EmailAlreadyExistsException("Email already exists");
        if (users.findByUsername(request.getUsername()).isPresent()) throw new UsernameAlreadyExistsException("Username already exists");
        return map(users.save(map(request)));
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        cleanData(request);
        if(users.findByUsername(request.getIdentifier()).isPresent()){
            User user = users.findByUsername(request.getIdentifier()).get();
            if(matches(request.getPassword(), user.getPassword())){
                return mapUserToLoginUserResponse(user);
            }
        }
        if (users.findByEmail(request.getIdentifier()).isPresent()) {
            User user = users.findByEmail(request.getIdentifier()).get();
            if(matches(request.getPassword(), user.getPassword())){
                return mapUserToLoginUserResponse(user);
            }
        }

        throw new UserDoesntExistException("Invalid login credentials");
    }


    private static void cleanData(LoginUserRequest request) {
        request.setIdentifier(request.getIdentifier().toLowerCase().trim());
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
}
