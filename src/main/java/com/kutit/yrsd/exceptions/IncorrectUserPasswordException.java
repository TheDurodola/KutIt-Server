package com.kutit.yrsd.exceptions;

public class IncorrectUserPasswordException extends RuntimeException {
    public IncorrectUserPasswordException(String oldPasswordIsIncorrect) {
        super(oldPasswordIsIncorrect);
    }
}
