package com.tektak.iloop.rm.datamodel;

import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */
public interface IUserDetail {
    public int getUserId();

    public void setUserId(int userId);

    public String getUserEmail();

    public void setUserEmail(String userEmail);

    public String getUserName();

    public void setUserName(String userName);

    public String getUserPassword();

    public void setUserPassword(String userPassword);

    public String getUserStatus();

    public void setUserStatus(String userStatus);

    public String getUserRole();

    public void setUserRole(String userRole);

    public Date getJoinDate();

    public void setJoinDate(Date joinDate);

}
