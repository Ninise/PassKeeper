package com.hazelhunt.passkeeper.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String stringToMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(str.getBytes(), 0, str.length());

        return new BigInteger(1, m.digest()).toString(16);
    }
}
