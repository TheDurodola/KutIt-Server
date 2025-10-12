package com.kutit.yrsd.exceptions;

public class InvalidLinkFormatException extends RuntimeException {
    public InvalidLinkFormatException(String invalidLink) {
        super(invalidLink);
    }


}
