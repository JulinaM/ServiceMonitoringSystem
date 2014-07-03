package com.tektak.iLoop.Rm.view.loggingSystem.log;

/**
 * Created by tektak on 7/3/14.
 */
public class LogFactory {
    public Log getLog(String LogType){
        if(LogType==null){
            return null;
        }
        if(LogType.equalsIgnoreCase("password")){
            return new Password();
        }else if(LogType.equalsIgnoreCase("profile")){
            return new Profile();
        }else if(LogType.equalsIgnoreCase("add")){
            return new Add();
        }else if(LogType.equalsIgnoreCase("Delete")){
            return new Delete();
        }
        return null;
    }
}
