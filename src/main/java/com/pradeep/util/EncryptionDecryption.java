package com.pradeep.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionDecryption {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = messageDigest.digest(input);
        return result;
    }

    public static String getSha3EncryptedString(String inputString) {
        byte[] input=inputString.getBytes(UTF_8);
        byte[] bytes=digest(input,"SHA3-256");
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return String.format(stringBuilder.toString());
    }
}
