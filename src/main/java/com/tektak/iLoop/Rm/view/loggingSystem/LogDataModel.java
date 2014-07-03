package com.tektak.iLoop.Rm.view.loggingSystem;

import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/3/14.
 */

/**
 * Datamodel for User Activity Log
 */
public class LogDataModel {
    /**
     * Store User Id
     */
    private String UID;
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
    public String getUID() {
        return UID;
    }

    /**
     * set UID
     * @param UID
     */
    public void setUID(String UID) {
        this.UID = UID;
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


}
