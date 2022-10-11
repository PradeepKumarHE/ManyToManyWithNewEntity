package com.pradeep.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
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

    // AES encryption, decryption new code
    public String encrypt(String rawkey, String value) {
        try {
            String initVector = "RandomInitVector"; // 16 bytes IV
            String padding = "9999999999999999999" + rawkey; // 128 bit key
            String key = padding.substring(padding.length() - 16, padding.length());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            //logger.info("encrypted string: " + Base64.encodeBase64String(encrypted));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
        }
        return null;
    }

    public String decrypt(String rawkey, String encrypted) {
        try {
            String initVector = "RandomInitVector"; // 16 bytes IV
            String padding = "9999999999999999999" + rawkey; // 128 bit key
            String key = padding.substring(padding.length() - 16, padding.length());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception e) {

        }
        return null;
    }
}
