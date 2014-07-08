package com.tektak.iloop.rm.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tektak on 7/8/14.
 */
public class PasswordEnc {
    public static byte[] encrypt(String email, String password){
        String raw = email+password;
        MessageDigest d = null;
        try {
            d = MessageDigest.getInstance("SHA-1");
            d.reset();
            d.update(raw.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return d.digest() ;
    }
}
