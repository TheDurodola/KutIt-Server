package com.kutit.yrsd.exceptions;

public class InvalidShortenLink extends RuntimeException {
    public InvalidShortenLink(String message) {
        super(message);
    }
}
