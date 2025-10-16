package com.kutit.yrsd.utils;

import com.kutit.yrsd.dtos.requests.CreateEntryRequest;
import com.kutit.yrsd.dtos.requests.CreateUserEntryRequest;
import com.kutit.yrsd.dtos.requests.RegisterUserRequest;
import com.kutit.yrsd.dtos.requests.UpdateUserAccountRequest;
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

    public static void validate(CreateUserEntryRequest request) {
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

    public static void validate(UpdateUserAccountRequest request) {
        validateEmail(request);
        validateName(request);
        validatePassword(request);
        validateUsername(request);
    }



    private static void validateUsername(UpdateUserAccountRequest request) {
        if (request.getUsername().isBlank()) throw new InvalidUsernameFormatException("Username field cannot be empty");
        if (request.getUsername().length() < 5) throw new InvalidUsernameFormatException("Username length must be at least 5");
        if (isValidUsername(request.getUsername())) throw new InvalidUsernameFormatException("Invalid username");
    }

    private static void validatePassword(UpdateUserAccountRequest request) {
        if (request.getNewPassword().equals("password")|| request.getNewPassword().equals("PASSWORD")) throw new InvalidPasswordFormatException("Password cannot be 'password' or 'PASSWORD'");
        if (request.getNewPassword().isBlank()) throw new InvalidPasswordFormatException("Password field cannot be empty");
        if (request.getNewPassword().length() < 6) throw new InvalidPasswordFormatException("Password length must be at least 6");
        if (isValidPassword(request.getNewPassword())) throw new InvalidPasswordFormatException("Invalid Password format");
    }

    private static void validateName(UpdateUserAccountRequest request) {
        validateFirstname(request);
        validateLastname(request);
    }

    private static void validateLastname(UpdateUserAccountRequest request) {
        if (request.getLastName().isBlank()) throw new InvalidNameFormatException("Lastname field cannot be empty");
        if (request.getLastName().length() < 3) throw new InvalidNameFormatException("Lastname length must be at least 3");
        if (isValidLastname(request.getLastName())) throw new InvalidNameFormatException("Kindly check your lastname");
    }

    private static void validateFirstname(UpdateUserAccountRequest request) {
        if (request.getFirstName().isBlank()) throw new InvalidNameFormatException("Firstname field cannot be empty");
        if (request.getFirstName().length() < 3) throw new InvalidNameFormatException("Firstname length must be at least 3");
        if (isValidFirstname(request.getFirstName())) throw new InvalidNameFormatException("Kindly check your firstname");
    }

    private static void validateEmail(UpdateUserAccountRequest request) {
        if (request.getEmail().isBlank()) throw new InvalidEmailFormatException("Email Field cannot be empty");
        if (!request.getEmail().contains(".")) throw new InvalidEmailFormatException("Email is missing .");
        if (!request.getEmail().contains("@")) throw new InvalidEmailFormatException("Email is missing @");
        if (isValidEmail(request.getEmail())) throw new InvalidEmailFormatException("Invalid Email");
    }


    private static void validateUsername(RegisterUserRequest request) {
        if (request.getUsername().isBlank()) throw new InvalidUsernameFormatException("Username field cannot be empty");
        if (request.getUsername().length() < 5) throw new InvalidUsernameFormatException("Username length must be at least 5");
        if (isValidUsername(request.getUsername())) throw new InvalidUsernameFormatException("Invalid username");
    }

    private static void validatePassword(RegisterUserRequest request) {
        if (request.getPassword().equals("password")|| request.getPassword().equals("PASSWORD")) throw new InvalidPasswordFormatException("Password cannot be 'password' or 'PASSWORD'");
        if (request.getPassword().isBlank()) throw new InvalidPasswordFormatException("Password field cannot be empty");
        if (request.getPassword().length() < 6) throw new InvalidPasswordFormatException("Password length must be at least 6");
        if (isValidPassword(request.getPassword())) throw new InvalidPasswordFormatException("Invalid Password format");
    }

    private static void validateName(RegisterUserRequest request) {
        validateFirstname(request);
        validateLastname(request);
    }

    private static void validateLastname(RegisterUserRequest request) {
        if (request.getLastName().isBlank()) throw new InvalidNameFormatException("Lastname field cannot be empty");
        if (request.getLastName().length() < 3) throw new InvalidNameFormatException("Lastname length must be at least 3");
        if (isValidLastname(request.getLastName())) throw new InvalidNameFormatException("Kindly check your lastname");
    }

    private static void validateFirstname(RegisterUserRequest request) {
        if (request.getFirstName().isBlank()) throw new InvalidNameFormatException("Firstname field cannot be empty");
        if (request.getFirstName().length() < 3) throw new InvalidNameFormatException("Firstname length must be at least 3");
        if (isValidFirstname(request.getFirstName())) throw new InvalidNameFormatException("Kindly check your firstname");
    }

    private static void validateEmail(RegisterUserRequest request) {
        if (request.getEmail().isBlank()) throw new InvalidEmailFormatException("Email Field cannot be empty");
        if (!request.getEmail().contains("@")) throw new InvalidEmailFormatException("Email is missing @");
        if (isValidEmail(request.getEmail())) throw new InvalidEmailFormatException("Invalid Email");
    }


    private static boolean isValidEmail(String string) {
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return !EMAIL_PATTERN.matcher(string).matches();
    }

    private static boolean isValidFirstname(String string) {
        Pattern FIRST_PATTERN = Pattern.compile("^[A-Za-z]+$");
        return !FIRST_PATTERN.matcher(string).matches();
    }

    private static boolean isValidLastname(String string) {
        Pattern LAST_PATTERN = Pattern.compile("^[A-Za-z]+(?:['-][A-Za-z]+)*$");
        return !LAST_PATTERN.matcher(string).matches();
    }

    private static boolean isValidPassword(String string) {
        Pattern LAST_PATTERN = Pattern.compile("^[A-Za-z0-9._$@%&!]{6,}$");
        return !LAST_PATTERN.matcher(string).matches();
    }

    private static boolean isValidUsername(String string) {
        Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9._-]{2,19}$");
        return !USERNAME_PATTERN.matcher(string).matches();
    }

}
