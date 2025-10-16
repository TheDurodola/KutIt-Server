package com.kutit.yrsd.exceptions;

public class EntryDoesntExistException extends RuntimeException {
    public EntryDoesntExistException(String message) {
        super(message);
    }
}
