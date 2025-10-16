package com.kutit.yrsd.exceptions;

public class EntriesDontExistsException extends RuntimeException {
    public EntriesDontExistsException(String message) {
        super(message);
    }
}
