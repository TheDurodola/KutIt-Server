package com.kutit.yrsd.exceptions;

public class UserDoesntExistException extends RuntimeException {
    public UserDoesntExistException(String message) {
        super(message);
    }
}
