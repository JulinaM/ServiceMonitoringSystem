package com.tektak.iloop.rm.model;

import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */
public class User implements  IUser{
    private String userId;
    private String userEmail;
    private String userName;
    private String userPassword;
    private String userStatus;
    private String userType;
    private String userRole;
    private Date joinDate;
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String getUserEmail() {
        return this.userEmail;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String getUserPassword() {
        return this.userPassword;
    }

    @Override
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String getUserStatus() {
        return this.userStatus;
    }

    @Override
    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String getUserType() {
        return this.userType;
    }

    @Override
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getUserRole() {
        return this.userRole;
    }

    @Override
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public Date getJoinDate() {
        return this.joinDate;
    }
}
