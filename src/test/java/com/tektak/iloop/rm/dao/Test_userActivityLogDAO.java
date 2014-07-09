package com.tektak.iloop.rm.dao;

import com.tektak.iLoop.rm.common.DateTime;
import com.tektak.iLoop.rm.common.RmException;
import com.tektak.iLoop.rm.dao.userActivityLogDAO;
import com.tektak.iLoop.rm.datamodel.UserActivityLogDataModel;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_userActivityLogDAO {
    //@Test
    public void Test_CreateLogTable(){
        System.out.println("<<<<<<<<<<<<Running Test_CreateLogTable()>>>>>>>>>>>>>>");

        try {

            new userActivityLogDAO().CreateLogTable();
            System.out.println("Test_CreateLogTable()");
            new userActivityLogDAO().closeDbConnection();

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
    public void Test_WriteLog(){
        UserActivityLogDataModel log=new UserActivityLogDataModel();
        log.setUID(3333);
        log.setIPaddress("170.0.33.1");
        log.setUserActivity("Delete of log operation testing");
        log.setTimestamp(DateTime.getTimestamp());
        int insertedRows= 0;
        try {
            insertedRows = new userActivityLogDAO().WriteLog(log);
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

    //@Test
    public void Test_ReadAllLog(){
        System.out.println("<<<<<<<<<<<<Running Test_ReadAllLog()>>>>>>>>>>>>>>");
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            UserActivityLogDataModel[] log=userActivityLogDAO.ReadAllLog();
            userActivityLogDAO.closeDbConnection();


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
            UserActivityLogDataModel[] log=userActivityLogDAO.ReadLogByUser("101");
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
            UserActivityLogDataModel[] log=userActivityLogDAO.ReadLogByDateTime("2014-07-08 11:12:10");
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
            UserActivityLogDataModel[] log=userActivityLogDAO.ReadLogByUserNDateTime("102", "2014-07-08");
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
