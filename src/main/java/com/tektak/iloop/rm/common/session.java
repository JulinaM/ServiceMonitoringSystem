package com.tektak.iloop.rm.common;

import com.tektak.iloop.rm.datamodel.UserDetail;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by tektak on 7/16/14.
 */
public class session {
    private int userId;
    private String userName;
    private int userRole;
    private String joinDate;
    private String userEmail;

    private static boolean validSession=false;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public static boolean IsValidSession(){
        return (validSession==true)?true:false;
    }
    public static void setSession(HttpServletRequest request,UserDetail userDetail){
        HttpSession httpSession=request.getSession(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userDetail.getUserId());
        jsonObject.put("userName", userDetail.getUserName());
        jsonObject.put("userEmail", userDetail.getUserEmail());
        jsonObject.put("userRole", userDetail.getUserRole());
        jsonObject.put("userJoinDate", userDetail.getJoinDate());

        httpSession.setAttribute("session", jsonObject.toString());
        validSession=true;
    }

    public static session getSession(HttpServletRequest request){
        HttpSession httpSession=request.getSession(false);
        String JSONString=(String)httpSession.getAttribute("session");
        if(JSONString==null){
           validSession=false;
           return null;
        }
        JSONObject jsonObject=new JSONObject(JSONString);
        session obj=new session();
        obj.setUserId(jsonObject.getInt("userId"));
        obj.setUserName(jsonObject.getString("userName"));
        obj.setUserRole(jsonObject.getInt("userRole"));
        obj.setJoinDate(jsonObject.getString("userJoinDate"));
        obj.setUserEmail(jsonObject.getString("userEmail"));
        return obj;
    }
}
