package com.tektak.iLoop.Rm.view.loggingSystem.log;

/**
 * Created by tektak on 7/3/14.
 */
public class Profile implements Log {

    @Override
    public String generateLog() {
        String UserActivity="Profile has been changed";
        System.out.println(UserActivity);
        return UserActivity;
    }
}
