package com.bot.exchanges.bittrex.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

    public static final String HMAC_SHA_256 = "HmacSHA256";
    public static final String HMAC_SHA_512 = "HmacSHA512";
    public static final String UTF_8 = "UTF-8";

    public static String calculateHash(String secret, String data, String algorithm) {
        try {
            Mac shaMac = Mac.getInstance(algorithm);
            shaMac.init(new SecretKeySpec(secret.getBytes(UTF_8), algorithm));
            return Hex.encodeHexString(shaMac.doFinal(data.getBytes(UTF_8)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateNonce() {
        return System.currentTimeMillis() + "";
    }
}
