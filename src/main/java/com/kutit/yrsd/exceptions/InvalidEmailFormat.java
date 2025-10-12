package com.kutit.yrsd.exceptions;

public class InvalidEmailFormat extends RuntimeException {
    public InvalidEmailFormat(String message) {
        super(message);
    }
}
