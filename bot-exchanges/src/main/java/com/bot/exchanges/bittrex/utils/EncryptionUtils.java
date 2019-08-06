package com.bot.exchanges.bittrex.utils;

import org.springframework.security.crypto.codec.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

    private static final String ALGORITHM = "HmacSHA512";

    public static String calculateHash(String secret, String data) {
        try {
            Mac shaMac = Mac.getInstance(ALGORITHM);
            shaMac.init(new SecretKeySpec(secret.getBytes(), ALGORITHM));
            return new String(Hex.encode(shaMac.doFinal(data.getBytes())));
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateNonce() {
        return System.currentTimeMillis() + "";
    }
}
