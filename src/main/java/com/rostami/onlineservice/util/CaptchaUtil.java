package com.rostami.onlineservice.util;

import java.util.Random;

public class CaptchaUtil {

    public static String generateCaptcha(int captchaLength) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder captchaString = new StringBuilder();

        // build a random captchaLength chars salt
        while (captchaString.length() < captchaLength) {
            int index = (int) (random.nextFloat() * characters.length());
            captchaString.append(characters.charAt(index));
        }
        return captchaString.toString();
    }
}
