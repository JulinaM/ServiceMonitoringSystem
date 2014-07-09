package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DateTime;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserActivityLogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_userActivityLogDAO {
    @Test
    public void Test_CreateLogTable(){
        userActivityLogDAO uDAO=null;
        try {
            uDAO=new userActivityLogDAO();
            uDAO.CreateLogTable();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }finally {
            try {
                uDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void Test_WriteLog(){
        UserActivityLogDM log=new UserActivityLogDM();
        log.setUID(3333);
        log.setIPaddress("170.0.0.2");
        log.setUserActivity("WriteLog(log) is testing...");
        log.setTimestamp(DateTime.getTimestamp());
        try {
            int insertedRows = new userActivityLogDAO().WriteLog(log);
            Assert.assertEquals(1, insertedRows);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }

    }

    @Test
    void Test_fetchLog(){
        UserActivityLogDM log=new UserActivityLogDM();
        log.setUID(3333);
        log.setIPaddress("170.0.0.3");
        log.setUserActivity("Fetchlog() method is testing...");
        log.setTimestamp(DateTime.getTimestamp());
        ResultSet rs=null;
        UserActivityLogDM[] userActivityLogDM= null;
        try {
            int insertedRows = new userActivityLogDAO().WriteLog(log);
            userActivityLogDAO userActivityLogDAO=new userActivityLogDAO();
            userActivityLogDM=userActivityLogDAO.fetchLog(rs);

        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_ReadAllLog(){
        System.out.println("<<<<<<<<<<<<Running Test_ReadAllLog()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            UserActivityLogDM[] log=userActivityLogDAO.ReadAllLog();
            userActivityLogDAO.closeDbConnection();
            System.out.println("=====================================================");
            for(int i=0;i<log.length;i++){
                System.out.println("UId::"+log[i].getUID());
                System.out.println("IPaddress::"+log[i].getIPaddress());
                System.out.println("UserActivity::"+log[i].getUserActivity());
                System.out.println("Timestamp::"+log[i].getTimestamp());
                System.out.println("=====================================================");
            }

        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void Test_ReadLogByUser(){
        System.out.println("<<<<<<<<<<<<Running Test_ReadLogByUser()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            UserActivityLogDM[] log=userActivityLogDAO.ReadLogByUser("101");
            System.out.println("=====================================================");
            for(int i=0;i<log.length;i++){
                System.out.println("UId::"+log[i].getUID());
                System.out.println("IPaddress::"+log[i].getIPaddress());
                System.out.println("UserActivity::"+log[i].getUserActivity());
                System.out.println("Timestamp::"+log[i].getTimestamp());
                System.out.println("=====================================================");
            }
            userActivityLogDAO.closeDbConnection();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void Test_ReadLogByDateTime(){
        System.out.println("<<<<<<<<<<<<Running Test_ReadLogByDateTime()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            UserActivityLogDM[] log=userActivityLogDAO.ReadLogByDateTime("2014-07-08 11:12:10");
            System.out.println("=====================================================");
            for(int i=0;i<log.length;i++){
                System.out.println("UId::"+log[i].getUID());
                System.out.println("IPaddress::"+log[i].getIPaddress());
                System.out.println("UserActivity::"+log[i].getUserActivity());
                System.out.println("Timestamp::"+log[i].getTimestamp());
                System.out.println("=====================================================");
            }
            userActivityLogDAO.closeDbConnection();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
    //@Test
    public void Test_ReadLogByUserNDateTime(){
        System.out.println("<<<<<<<<<<<<Running Test_ReadLogByUserNDateTime()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            UserActivityLogDM[] log=userActivityLogDAO.ReadLogByUserNDateTime("102", "2014-07-08");
            System.out.println("=====================================================");
            for(int i=0;i<log.length;i++){
                System.out.println("UId::"+log[i].getUID());
                System.out.println("IPaddress::"+log[i].getIPaddress());
                System.out.println("UserActivity::"+log[i].getUserActivity());
                System.out.println("Timestamp::"+log[i].getTimestamp());
                System.out.println("=====================================================");
            }
            userActivityLogDAO.closeDbConnection();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void Test_deleteLogByUser(){
        System.out.println("<<<<<<<<<<<<Running Test_deleteLogByUser()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            System.out.println(userActivityLogDAO.deleteLogByUser("101")+" log are Deleted!!");
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }

    //@Test
    public void Test_deleteLogByDateTime(){
        System.out.println("<<<<<<<<<<<<Running Test_deleteLogByDateTime()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            System.out.println(userActivityLogDAO.deleteLogByDateTime("13:16")+" log are Deleted!!");
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }

    //@Test
    public void Test_deleteLogByUserNDateTime(){
        System.out.println("<<<<<<<<<<<<Running Test_deleteLogByUserNDateTime()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            System.out.println(userActivityLogDAO.deleteLogByUserNDateTime("3333","2014-07-08")+" log are Deleted!!");
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }

}
