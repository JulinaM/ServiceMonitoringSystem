package com.tektak.iloop.rm.common;

/**
 * Created by tektak on 7/4/14.
 */

/**
 * Custome Exception
 */
public interface RmException {
    public static final String CONNECTION_ERROR="Database Connection Failure!!";

    /**
     * Database connection Error Exception
     */
    public class DBConnectionError extends Exception{
        public DBConnectionError(String message,Throwable throwable){
            super(message,throwable);
        }
        public DBConnectionError(String message){
            super(message);
        }
    }
}
