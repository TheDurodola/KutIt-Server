package com.kutit.yrsd.utils;

import com.kutit.yrsd.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "email");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(InvalidLinkFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNameFormatException(InvalidLinkFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "name");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(EntriesDontExistsException.class)
    public ResponseEntity<Map<String, String>> handleEntriesDontExistsException(EntriesDontExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "entries");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(EntryDoesntExistException.class)
    public ResponseEntity<Map<String, String>> handleEntryDoesntExistException(EntryDoesntExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "entry");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }


    @ExceptionHandler(IncorrectUserPasswordException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectUserPasswordException(IncorrectUserPasswordException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "password");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }


    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEmailFormat(InvalidEmailFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "email");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(InvalidNameFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidNameFormatException(InvalidNameFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "name");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPasswordFormat(InvalidPasswordFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "password");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(InvalidShortenLinkException.class)
    public ResponseEntity<Map<String, String>> handleInvalidShortenLink(InvalidShortenLinkException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "link");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(InvalidUsernameFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUsernameFormat(InvalidUsernameFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "username");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Map<String, String>> handleInvalidUsernameFormat(UnauthorizedUserException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "username");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }


    @ExceptionHandler(UserDoesntExistException.class)
    public ResponseEntity<Map<String, String>> handleUserDoesntExistException(UserDoesntExistException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "user");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }


    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "username");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(InvalidEmailOrUsernameFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEmailOrUsernameFormatException(InvalidEmailOrUsernameFormatException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "username/email");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<Map<String, String>> handleUserNotLoggedInException(UserNotLoggedInException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "user");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "unknown");
        errors.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(404)).body(errors);
    }




}
