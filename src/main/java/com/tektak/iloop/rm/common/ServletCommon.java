package com.tektak.iloop.rm.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by tektak on 7/15/14.
 */
public class ServletCommon {
    private static String success = null;
    private static String error = null;

    public static String generateToken(HttpSession httpSession) {
        String token;
        if (httpSession.getAttribute("token") == null) {
            token = PasswordEnc.md5Hash(String.valueOf(DateTime.getTimestamp()));
            httpSession.setAttribute("token", token);
        } else {
            token = (String) httpSession.getAttribute("token");
        }
        return token;
    }

    public static void setSuccessMsg(String key) {
        success = CommonConfig.getConfig().ReadString(key);
    }

    public static void setErrorMsg(String key) {
        error = CommonConfig.getConfig().ReadString(key);
    }

    public static void getMessage(HttpServletRequest request) {
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        ServletCommon.setSuccessMsg(null);
        ServletCommon.setErrorMsg(null);
    }


}
