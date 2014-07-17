package com.tektak.iloop.rm.common;

import javax.servlet.http.HttpServletRequest;
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
        } else {
            token = (String) httpSession.getAttribute("token");
        }
        return token;
    }

    public static void setErrMsg(HttpServletRequest request,String errMsg){
        request.setAttribute("errMsg",errMsg);
    }

    public static String getErrMsg(HttpServletRequest request){
        String errMsg=(String)request.getAttribute("errMsg");
        if(errMsg==null)
            return null;
        return errMsg;
    }



}
