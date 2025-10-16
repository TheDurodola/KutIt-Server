package com.kutit.yrsd.exceptions;

public class InvalidShortenLinkException extends RuntimeException {
    public InvalidShortenLinkException(String message) {
        super(message);
    }
}
