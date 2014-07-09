package com.tektak.iLoop.rm.common;

import java.sql.Timestamp;

/**
 * Created by tektak on 7/7/14.
 */
public class DateTime {
    public static Timestamp getTimestamp(){
        java.util.Date date= new java.util.Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        return timestamp;
    }
}
