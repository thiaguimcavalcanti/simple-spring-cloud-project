package com.bot.exchanges.bittrex.utils;

import org.springframework.security.crypto.codec.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

    public static final String HMAC_SHA_256 = "HmacSHA256";
    public static final String HMAC_SHA_512 = "HmacSHA512";

    public static String calculateHash(String secret, String data, String algorithm) {
        try {
            Mac shaMac = Mac.getInstance(algorithm);
            shaMac.init(new SecretKeySpec(secret.getBytes(), algorithm));
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
