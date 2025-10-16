package com.kutit.yrsd.exceptions;

public class InvalidEmailOrUsernameFormatException extends RuntimeException {
    public InvalidEmailOrUsernameFormatException(String message) {
        super(message);
    }
}
