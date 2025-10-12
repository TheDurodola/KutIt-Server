package com.kutit.yrsd.utils;

import com.kutit.yrsd.dtos.requests.CreateEntryRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.exceptions.*;

import java.util.regex.Pattern;

public class Validator {

    public static void validate(CreateEntryRequest request) {
        String link = request.getOriginalLink();
        Pattern pattern = Pattern.compile(
                "^(?i)(?:https?://)?(?:(?:[a-z0-9-]+\\.)+[a-z]{2,}|localhost)(?::\\d+)?(?:/\\S*)?$"
        );

        if(!pattern.matcher(link).matches()){
            throw new InvalidLinkFormatException("Invalid URL. Kindly Check your URL");
        }
    }

    public static void validate(RegisterUserRequest request){
        validateEmail(request);
        validateName(request);
        validatePassword(request);
        validateUsername(request);
    }

    private static void validateUsername(RegisterUserRequest request) {
        if (request.getUsername().isBlank()) throw new InvalidUsernameFormat("Username field cannot be empty");
        if (request.getUsername().length() < 5) throw new InvalidUsernameFormat("Username length must be at least 5");
//        if (!isValidUsername(request.getUsername())) throw new InvalidUsernameFormat("Invalid username");
    }

    private static void validatePassword(RegisterUserRequest request) {
        if (request.getPassword().isBlank()) throw new InvalidPasswordFormat("Password field cannot be empty");
        if (request.getPassword().length() < 6) throw new InvalidPasswordFormat("Password length must be at least 6");
//        if (!isValidPassword(request.getPassword())) throw new InvalidPasswordFormat("Invalid Password format");
    }

    private static void validateName(RegisterUserRequest request) {
        validateFirstname(request);
        validateLastname(request);
    }

    private static void validateLastname(RegisterUserRequest request) {
        if (request.getLastName().isBlank()) throw new InvalidNameFormat("Lastname field cannot be empty");
        if (request.getLastName().length() < 3) throw new InvalidNameFormat("Lastname length must be at least 3");
        if (!isValidLastname(request.getLastName())) throw new InvalidNameFormat("Kindly check your lastname");
    }

    private static void validateFirstname(RegisterUserRequest request) {
        if (request.getFirstName().isBlank()) throw new InvalidNameFormat("Firstname field cannot be empty");
        if (request.getFirstName().length() < 3) throw new InvalidNameFormat("Firstname length must be at least 3");
        if (!isValidFirstname(request.getFirstName())) throw new InvalidNameFormat("Kindly check your firstname");
    }

    private static void validateEmail(RegisterUserRequest request) {
        if (request.getEmail().isBlank()) throw new InvalidEmailFormat("Email Field cannot be empty");
        if (!request.getEmail().contains("@")) throw new InvalidEmailFormat("Email is missing @");
        if (!isValidEmail(request.getEmail())) throw new InvalidEmailFormat("Invalid Email");
    }


    private static boolean isValidEmail(String string) {
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return EMAIL_PATTERN.matcher(string).matches();
    }

    private static boolean isValidFirstname(String string) {
        Pattern FIRST_PATTERN = Pattern.compile("^[A-Za-z]+$");
        return FIRST_PATTERN.matcher(string).matches();
    }

    private static boolean isValidLastname(String string) {
        Pattern LAST_PATTERN = Pattern.compile("^[A-Za-z]+(?:['-][A-Za-z]+)*$");
        return LAST_PATTERN.matcher(string).matches();
    }

//    private static boolean isValidPassword(String string) {
//        Pattern LAST_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$");
//        return LAST_PATTERN.matcher(string).matches();
//    }


//    private static boolean isValidUsername(String string) {
//        Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9._-]{2,19}$\n");
//        return USERNAME_PATTERN.matcher(string).matches();
//    }

}
