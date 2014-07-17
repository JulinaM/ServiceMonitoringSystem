package com.tektak.iloop.rm.common;

import com.tektak.iloop.rm.datamodel.UserDetail;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by tektak on 7/16/14.
 */
public class OurSession {
    public static void setSession(HttpSession httpSession,UserDetail userDetail){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userDetail.getUserId());
        jsonObject.put("userName", userDetail.getUserName());
        jsonObject.put("userEmail", userDetail.getUserEmail());
        jsonObject.put("userRole", userDetail.getUserRole());
        jsonObject.put("userJoinDate", userDetail.getJoinDate().getTime());

        httpSession.setAttribute("session", jsonObject.toString());

    }

    public static UserDetail getSession(HttpSession httpSession){
        String JSONString=(String)httpSession.getAttribute("session");
        if(JSONString==null){
           return null;
        }
        JSONObject jsonObject=new JSONObject(JSONString);
        UserDetail userDetail=new UserDetail();
        userDetail.setUserId(jsonObject.getInt("userId"));
        userDetail.setUserName(jsonObject.getString("userName"));
        userDetail.setUserRole(jsonObject.getString("userRole"));
        userDetail.setJoinDate( new Date(jsonObject.getLong("userJoinDate")));
        userDetail.setUserEmail(jsonObject.getString("userEmail"));
        return userDetail;
    }
}
