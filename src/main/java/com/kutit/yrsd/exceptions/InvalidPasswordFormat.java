package com.kutit.yrsd.exceptions;

public class InvalidPasswordFormat extends RuntimeException {
    public InvalidPasswordFormat(String message) {
        super(message);
    }
}
