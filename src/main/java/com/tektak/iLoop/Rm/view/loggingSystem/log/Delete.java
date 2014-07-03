package com.tektak.iLoop.Rm.view.loggingSystem.log;

/**
 * Created by tektak on 7/3/14.
 */
public class Delete implements Log {
    @Override
    public String generateLog() {
        String UserActivity="Deleted something";
        System.out.println(UserActivity);
        return UserActivity;
    }
}
