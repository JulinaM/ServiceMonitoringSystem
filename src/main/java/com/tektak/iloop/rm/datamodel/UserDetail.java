package com.tektak.iloop.rm.datamodel;

import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */
public class UserDetail implements IUserDetail {
    private int userId;
    private String userEmail;
    private String userName;
    private String userPassword;
    private String userStatus;
    private String userRole;
    private Date joinDate;

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUserEmail() {
        return this.userEmail;
    }

    @Override
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserPassword() {
        return this.userPassword;
    }

    @Override
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String getUserStatus() {
        return this.userStatus;
    }

    @Override
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String getUserRole() {
        return this.userRole;
    }

    @Override
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public Date getJoinDate() {
        return this.joinDate;
    }

    @Override
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}


