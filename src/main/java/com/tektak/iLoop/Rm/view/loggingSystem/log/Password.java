package com.tektak.iLoop.Rm.view.loggingSystem.log;

/**
 * Created by tektak on 7/3/14.
 */
public class Password implements Log {
    @Override
    public String generateLog() {
        String UserActivity="Password has been changed.";
        System.out.println(UserActivity);
        return UserActivity;
    }
}
