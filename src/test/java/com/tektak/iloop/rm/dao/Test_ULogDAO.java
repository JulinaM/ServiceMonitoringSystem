package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.DateTime;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

import org.junit.Test;
import org.junit.Assert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_ULogDAO {
    @Test
    public void Test_CreateLogTable(){
        ULogDAO uDAO=null;
        try {
            uDAO=new ULogDAO();
            int i=uDAO.CreateLogTable();
            if(i==0)
                Assert.assertTrue(i==0);
            else if(i==1)
                Assert.assertTrue(i==1);
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
        ULogDM log=new ULogDM();
        log.setUID(2222);
        log.setIPaddress("170.0.0.2");
        log.setUserActivity("WriteLog(log) is testing...");
        log.setTimestamp(DateTime.getTimestamp());
        try {

            int insertedRows = new ULogDAO().WriteLog(log);
            Assert.assertTrue(insertedRows == 1);

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
    public void Test_fetchLog(){

        ULogDAO uDAO=null;
        try {
            ULogDAO ULogDAO =new ULogDAO();
            ULogDAO.deleteLogByUser(3333);

            ULogDM Activitylog=new ULogDM();
            Activitylog.setUID(3333);

            Activitylog.setIPaddress("170.0.0.3");
            Activitylog.setUserActivity("Fetchlog() method is testing...");
            Timestamp timestamp=Timestamp.valueOf("2007-09-23 10:10:10.0");
            Activitylog.setTimestamp(timestamp);

            uDAO=new ULogDAO();
            uDAO.WriteLog(Activitylog);

            MySql mySql=new DBConnection().Connect();
            MySqlQuery mySqlQuery=new MySqlQuery();
            mySqlQuery.setSql(mySql);
            mySqlQuery.setQuery("SELECT * FROM UserActivityLog WHERE UId=?");
            mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=mySqlQuery.getPreparedStatement();
            ps.setInt(1, Activitylog.getUID());

            ResultSet rs=mySqlQuery.Drl();
            rs.next();
            ULogDM logFetched=new ULogDM();
            logFetched.setUID(rs.getInt("UId"));
            logFetched.setIPaddress(rs.getString("IPaddress"));
            logFetched.setUserActivity(rs.getString("Activity"));
            logFetched.setTimestamp(rs.getTimestamp("Timestamp"));

           Assert.assertTrue(Activitylog.equals(logFetched));

        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                uDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void Test_ReadAllLog(){
        ULogDAO ULogDAO =null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteAllLog();

            ULogDM[] Activitylog=new ULogDM[5];
            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                Activitylog[i].setUID(i);
                Activitylog[i].setIPaddress("174.4.4."+i);
                Activitylog[i].setUserActivity("ReadAllLog() method is testing..."+i);
                Activitylog[i].setTimestamp(Timestamp.valueOf("2007-09-23 10:10:10.0"));
                ULogDAO.WriteLog(Activitylog[i]);
            }
            ULogDM[] log= ULogDAO.ReadAllLog();
            Assert.assertTrue(log.length == 5);
            Assert.assertTrue(ULogDM.equals(log, Activitylog));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void Test_ReadLogByUser(){
        ULogDAO ULogDAO =null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteLogByUser(5555);

            ULogDM[] Activitylog=new ULogDM[5];
            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                Activitylog[i].setUID(5555);
                Activitylog[i].setIPaddress("174.5.5." + i);
                Activitylog[i].setUserActivity("ReadLogByUser() method is testing..." + i);
                Activitylog[i].setTimestamp(Timestamp.valueOf("2007-09-23 10:10:10.0"));
                ULogDAO.WriteLog(Activitylog[i]);
            }
            ULogDM[] log= ULogDAO.ReadLogByUser(5555);

            Assert.assertTrue(log.length == 5);
            Assert.assertTrue(ULogDM.equals(log, Activitylog));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void Test_ReadLogByDateTimeGreaterThan(){
        ULogDAO ULogDAO =null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteLogByDateTimeGreaterThan(Timestamp.valueOf("2014-10-10 10:10:20.0"));

            ULogDM[] Activitylog=new ULogDM[5];
            int sec=0;
            Timestamp original=Timestamp.valueOf("2014-10-10 10:10:20.0");

            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                sec=(i+1)*60;
                Timestamp newTime = new Timestamp(original.getTime()+(sec*1000L));

                Activitylog[i].setUID(i);
                Activitylog[i].setIPaddress("174.6.6." + i);
                Activitylog[i].setUserActivity("ReadLogByDateTimeGreaterThan() method is testing..." + i);
                Activitylog[i].setTimestamp(newTime);
                ULogDAO.WriteLog(Activitylog[i]);
            }

            ULogDM[] log= ULogDAO.ReadLogByDateTimeGreaterThan(Timestamp.valueOf("2014-10-10 10:10:20.0"));

            Assert.assertTrue(log.length == 5);
            Assert.assertTrue(ULogDM.equals(log, Activitylog));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void Test_ReadLogByDateTimeLessThan(){
        ULogDAO ULogDAO =null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteLogByDateTimeLessThan(Timestamp.valueOf("2010-10-10 10:10:10.0"));

            ULogDM[] Activitylog=new ULogDM[5];
            int sec=0;
            Timestamp original=Timestamp.valueOf("2010-10-10 10:10:10.0");

            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                sec=(i+1)*60;
                Timestamp newTime = new Timestamp(original.getTime()-(sec*1000L));

                Activitylog[i].setUID(i);
                Activitylog[i].setIPaddress("174.7.7." + i);
                Activitylog[i].setUserActivity("ReadLogByDateTimeLessThan() method is testing..." + i);
                Activitylog[i].setTimestamp(newTime);
                ULogDAO.WriteLog(Activitylog[i]);
            }

            ULogDM[] log= ULogDAO.ReadLogByDateTimeLessThan(Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(log.length == 5);
            Assert.assertTrue(ULogDM.equals(log, Activitylog));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void Test_ReadLogByUserNDateTimeLessThan(){
        ULogDAO ULogDAO =null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteLogByUserNDateTimeLessThan(6666,Timestamp.valueOf("2010-10-10 10:10:10.0"));

            ULogDM[] Activitylog=new ULogDM[5];
            int sec=0;
            Timestamp original=Timestamp.valueOf("2010-10-10 10:10:10.0");

            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                sec=(i+1)*60;
                Timestamp newTime = new Timestamp(original.getTime()-(sec*1000L));

                Activitylog[i].setUID(6666);
                Activitylog[i].setIPaddress("174.7.7." + i);
                Activitylog[i].setUserActivity("ReadLogByUserNDateTimeLessThan() method is testing..." + i);
                Activitylog[i].setTimestamp(newTime);
                ULogDAO.WriteLog(Activitylog[i]);
            }

            ULogDM[] log= ULogDAO.ReadLogByUserNDateTimeLessThan(6666,Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(log.length == 5);
            Assert.assertTrue(ULogDM.equals(log, Activitylog));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void Test_ReadLogByUserNDateTimeGreaterThan(){

        ULogDAO ULogDAO =null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteLogByUserNDateTimeGreaterThan(7777,Timestamp.valueOf("2010-10-10 10:10:10.0"));

            ULogDM[] Activitylog=new ULogDM[5];
            int sec=0;
            Timestamp original=Timestamp.valueOf("2010-10-10 10:10:10.0");

            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                sec=(i+1)*60;
                Timestamp newTime = new Timestamp(original.getTime()+(sec*1000L));

                Activitylog[i].setUID(7777);
                Activitylog[i].setIPaddress("174.7.7." + i);
                Activitylog[i].setUserActivity("ReadLogByUserNDateTimeGreaterThan() method is testing..." + i);
                Activitylog[i].setTimestamp(newTime);
                ULogDAO.WriteLog(Activitylog[i]);
            }

            ULogDM[] log= ULogDAO.ReadLogByUserNDateTimeGreaterThan(7777,Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(log.length == 5);
            Assert.assertTrue(ULogDM.equals(log, Activitylog));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
    }
}
