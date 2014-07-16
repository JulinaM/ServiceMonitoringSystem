package com.tektak.iloop.rm.datamodel;

import java.sql.Timestamp;

/**
 * Created by tektak on 7/3/14.
 */

/**
 * Datamodel for User Activity Log
 */
public class ULogDM {
    /**
     * Store User Id
     */
    private int UId;
    private int logId;
    /**
     * Store IP address of user
     */
    private String IPaddress;
    private UserDetail userDetail=null;
    /**
     * Store User Activity
     */
    private String UserActivity;
    /**
     * store Timestamp of User Activity
     */
    private Timestamp timestamp;


    /**
     *
     * @return UID
     */
    public int getUID() {
        return UId;
    }

    /**
     * set UID
     * @param UId
     */
    public void setUID(int UId) {
        this.UId = UId;
    }

    /**
     *
     * @return UserActivity
     */
    public String getUserActivity() {
        return UserActivity;
    }

    /**
     * set UserActivity
     * @param userActivity
     */
    public void setUserActivity(String userActivity) {
        UserActivity = userActivity;
    }

    /**
     *
     * @return timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * set timestamp
     * @param timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return IP address
     */
    public String getIPaddress() {
        return IPaddress;
    }

    /**
     * Set IP address of user
     * @param IPaddress
     */
    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    public boolean equals(ULogDM obj){
        if(this.userDetail.getUserName().equals(obj.userDetail.getUserName())&&this.UId==obj.UId&&this.IPaddress.equals(obj.IPaddress)&&this.UserActivity.equals(obj.UserActivity)&&this.timestamp.equals(obj.timestamp)){
            return true;
        }else
        return false;
    }
    public static boolean equals(ULogDM[] obj1,ULogDM[] obj2){
        int obj1Len=obj1.length;
        int obj2Len=obj2.length;
        if(obj1Len!=obj2Len){
            return false;
        }
        for(int i=0;i<obj1Len;i++){
            if(!obj1[i].equals(obj2[i])){
                return false;
            }
        }
        return true;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
