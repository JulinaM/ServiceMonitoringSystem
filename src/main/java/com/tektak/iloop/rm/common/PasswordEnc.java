package com.tektak.iloop.rm.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by tektak on 7/8/14.
 * This class is for password encryption and  create random password text
 */
public class PasswordEnc {
    /**
     * Method to encrypt password
     *
     * @param salt email id is used as salt
     * @param data password is data
     * @return sha512 encrypted password
     */
    public static String encrypt(String salt, String data) {
        return DigestUtils.sha512Hex(salt + data);
    }

    /**
     * Method to create random string
     *
     * @return random string of length 10 which is used as password while creating new user
     */
    public static String createRandomString() {
        String data = RandomStringUtils.randomAlphanumeric(10);
        System.out.println("pass: " + data);
        return data;
    }
}
