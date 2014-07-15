package com.tektak.iloop.rm.common;

import javax.servlet.http.HttpSession;

/**
 * Created by tektak on 7/15/14.
 */
public class ServletCommon {
    public static String generateToken(HttpSession httpSession){
        String token;
        if(httpSession.getAttribute("token")==null){
            token = PasswordEnc.md5Hash(String.valueOf(DateTime.getTimestamp()));
            httpSession.setAttribute("token", token);
        }else{
            token = (String)httpSession.getAttribute("token");
        }
        return token;
    }
}
