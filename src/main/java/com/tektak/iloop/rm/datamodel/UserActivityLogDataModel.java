package com.tektak.iLoop.rm.datamodel;

import java.sql.Timestamp;

/**
 * Created by tektak on 7/3/14.
 */

/**
 * Datamodel for User Activity Log
 */
public class UserActivityLogDataModel {
    /**
     * Store User Id
     */
    private int UId;
    /**
     * Store IP address of user
     */
    private String IPaddress;
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
}
