package br.com.jcv.treinadorpro.infrastructure.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    private final static String SHA256_ALGO = "SHA-256";
    private final static String MD5_ALGO = "MD5";

    public static String hashSHA256(String password) {
        return hash(password, SHA256_ALGO);
    }

    public static String hashMD5(String password) {
        return hash(password, MD5_ALGO);
    }

    private static String hash(String password, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash algorithm " + algorithm, e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
