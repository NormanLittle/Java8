package com.sandbox;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

import static com.google.common.hash.Hashing.sha256;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class SoapAuthentication {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String now = "2018-05-23T014:10:00Z";
        String password = "Welcome100";
        String login = "soap-test";

        System.out.printf("SHA256: %s%n", sha(now, password, login));
        System.out.printf("MD5: %s%n", md5(now, password));
    }

    private static String sha(String now, String password, String login) {
        return hash(now +
                    hash(password +
                         hash(login)));
    }

    private static String hash(String value) {
        return sha256().hashString(value, UTF_8).toString();
    }

    private static String md5(String now, String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        messageDigest.update((now + digest(messageDigest.digest())).getBytes());

        return digest(messageDigest.digest());
    }

    private static String digest(byte[] value) {
        return leftPad(new BigInteger(1, value).toString(16), 32, '0');
    }
}
