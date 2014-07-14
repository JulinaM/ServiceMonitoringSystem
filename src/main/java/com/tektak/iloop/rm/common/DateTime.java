package com.tektak.iloop.rm.common;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by tektak on 7/7/14.
 */
public class DateTime {
    public static Timestamp getTimestamp() {
        Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
