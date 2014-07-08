package com.tektak.iloop.rm.datamodel;

import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */
public interface IUserDetail {
    public void setUserId(Integer userId);
    public Integer getUserId();
    public void setUserEmail(String userEmail);
    public String getUserEmail();
    public void setUserName(String userName);
    public String getUserName();
    public void setUserPassword(String userPassword);
    public String getUserPassword();
    public void setUserStatus(Integer userStatus);
    public Integer getUserStatus();
    public void setUserRole(Integer userRole);
    public Integer getUserRole();
    public void setJoinDate(Date joinDate);
    public Date getJoinDate();

}
