package com.kutit.yrsd.utils;

import java.util.Random;

public class ShortLinkGenerator {
    public static String generateShortCode() {
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
