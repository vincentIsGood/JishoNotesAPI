package com.vincentcodes.jishoapi.utils;

import java.util.Base64;
import java.util.Random;

/**
 * SecureRandom is not used.
 */
public class RandomStringGenerator {
    private static final Random random = new Random();

    /**
     * This is not as fast as spiting out raw alphanumeric result.
     * @return encoded base64 string
     */
    public static String generate(int lengthBytes){
        byte[] bytes = new byte[lengthBytes];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
