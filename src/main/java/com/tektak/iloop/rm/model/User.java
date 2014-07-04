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
    private Integer userStatus;
    private Integer userRole;
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
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public Integer getUserStatus() {
        return this.userStatus;
    }

    @Override
    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    @Override
    public Integer getUserRole() {
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
