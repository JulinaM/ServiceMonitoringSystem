package com.tektak.iLoop.Rm.view.loggingSystem.log;

/**
 * Created by tektak on 7/3/14.
 */
public class Add implements Log {
    @Override
    public String generateLog() {

        String UserActivity="Added something";
        System.out.println(UserActivity);
        return UserActivity;
    }
}
