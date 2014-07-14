package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_ULogDAO {
    //@Test
    public void Test_CreateLogTable() {
        ULogDAO uDAO = null;
        try {
            uDAO = new ULogDAO();
            int i = uDAO.CreateLogTable();
            if (i == 0)
                Assert.assertTrue(i == 0);
            else if (i == 1)
                Assert.assertTrue(i == 1);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } finally {
            uDAO.closeDbConnection();
        }
    }

    //@Test
    public void Test_getRecentLogId(){
        try {
            ULogDAO uLogDAO=new ULogDAO();
            System.out.println("Recent Log Id="+uLogDAO.getRecentLogId());
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
    public void Test_WriteLog() {
        ULogDM log = new ULogDM();
        ULogDAO uLogDAO = null;
        log.setUID(2222);
        log.setIPaddress("170.0.0.2");
        log.setUserActivity("WriteLog() is testing...");
        log.setTimestamp(Timestamp.valueOf("2010-10-10 10:10:10.0"));
        try {
            uLogDAO = new ULogDAO();
            uLogDAO.WriteLog(log);
            ULogDM[] uLogDMs=uLogDAO.ReadAllLog();
            uLogDAO.deleteAllLog();
            Assert.assertTrue(log.equals(uLogDMs[0]));
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } finally {
            uLogDAO.closeDbConnection();
        }

    }

    //@Test
    public void Test_fetchLog() {

        ULogDAO uDAO = null;
        try {
            ULogDAO ULogDAO = new ULogDAO();
            ULogDAO.deleteLogByUser(3333);

            ULogDM Activitylog = new ULogDM();
            Activitylog.setUID(3333);

            Activitylog.setIPaddress("170.0.0.3");
            Activitylog.setUserActivity("Fetchlog() method is testing...");
            Timestamp timestamp = Timestamp.valueOf("2010-10-10 10:10:10.0");
            Activitylog.setTimestamp(timestamp);

            uDAO = new ULogDAO();
            uDAO.WriteLog(Activitylog);

            MySql mySql = new DBConnection().Connect();
            MySqlQuery mySqlQuery = new MySqlQuery();
            mySqlQuery.setSql(mySql);
            mySqlQuery.setQuery("SELECT * FROM UserActivityLog WHERE UId=?");
            mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, Activitylog.getUID());

            ResultSet rs = mySqlQuery.Drl();
            rs.next();
            ULogDM logFetched = new ULogDM();
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
        } finally {
            uDAO.closeDbConnection();
        }
    }

    //@Test
    public void Test_ReadAllLog() {
        ULogDAO ULogDAO = null;
        try {
            ULogDM Activitylog = new ULogDM();
            ULogDAO = new ULogDAO();

            Activitylog.setUID(4444);
            Activitylog.setIPaddress("174.4.4.0");
            Activitylog.setUserActivity("ReadAllLog() method is testing...");
            Activitylog.setTimestamp(Timestamp.valueOf("2010-10-10 10:10:10.0"));
            ULogDAO.WriteLog(Activitylog);

            ULogDM[] log = ULogDAO.ReadAllLog();
            int dRows=ULogDAO.deleteAllLog();
            System.out.println("Deleted Rows:"+dRows);
            Assert.assertTrue(Activitylog.equals(log[0]));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } finally {
            ULogDAO.closeDbConnection();
        }
    }

    //@Test
    public void Test_ReadLogByUser() {
        ULogDAO ULogDAO = null;
        try {
            ULogDAO = new ULogDAO();


            ULogDM Activitylog = new ULogDM();

            Activitylog.setUID(5555);
            Activitylog.setIPaddress("174.5.5.0");
            Activitylog.setUserActivity("ReadLogByUser() method is testing...");
            Activitylog.setTimestamp(Timestamp.valueOf("2011-11-11 10:10:10.0"));
            ULogDAO.WriteLog(Activitylog);

            ULogDM[] log = ULogDAO.ReadLogByUser(5555);

            Assert.assertTrue(Activitylog.equals(log[0]));
            ULogDAO.deleteLogByUser(5555);
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } finally {
            ULogDAO.closeDbConnection();
        }
    }

    //@Test
    public void Test_ReadLogByDateTimeGreaterThan() {
        ULogDAO ULogDAO = null;
        try {
            ULogDAO = new ULogDAO();

            ULogDM Activitylog = new ULogDM();
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2014-10-10 10:10:20.0");


            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() + (sec * 1000L));

            Activitylog.setUID(6666);
            Activitylog.setIPaddress("174.6.6.0");
            Activitylog.setUserActivity("ReadLogByDateTimeGreaterThan() method is testing...");
            Activitylog.setTimestamp(newTime);
            ULogDAO.WriteLog(Activitylog);

            ULogDM[] log = ULogDAO.ReadLogByDateTimeGreaterThan(Timestamp.valueOf("2014-10-10 10:10:20.0"));

            ULogDAO.deleteLogByDateTimeGreaterThan(Timestamp.valueOf("2014-10-10 10:10:20.0"));
            Assert.assertTrue(Activitylog.equals(log[0]));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } finally {
            ULogDAO.closeDbConnection();
        }
    }

    @Test
    public void Test_ReadLogByDateTimeLessThan() {
        ULogDAO ULogDAO = null;
        try {
            ULogDAO = new ULogDAO();

            ULogDM Activitylog = new ULogDM();
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");

            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() - (sec * 1000L));

            Activitylog.setUID(77777);
            Activitylog.setIPaddress("174.7.7.2");
            Activitylog.setUserActivity("ReadLogByDateTimeLessThan() method is testing...");
            Activitylog.setTimestamp(newTime);

            int insertedRows=ULogDAO.WriteLog(Activitylog);
            ULogDAO.deleteLogByLogId(0);
            ULogDM[] log = ULogDAO.ReadLogByDateTimeLessThan(Timestamp.valueOf("2010-10-10 10:10:10.0"));
            int i=0;
            for(ULogDM u:log) {
                System.out.println(insertedRows + "---------------->>" + log[i].getUID());
                System.out.println("---------------->>" + log[i].getUserActivity());
                System.out.println("---------------->>" + log[i].getIPaddress());
                System.out.println("---------------->>" + log[i++].getTimestamp());
            }

            Assert.assertTrue(Activitylog.equals(log[0]));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } finally {
            ULogDAO.closeDbConnection();
        }
    }

    //@Test
    public void Test_ReadLogByUserNDateTimeLessThan() {
        ULogDAO ULogDAO = null;
        try {
            ULogDAO = new ULogDAO();

            ULogDM Activitylog = new ULogDM();
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");
            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() - (sec * 1000L));

            Activitylog.setUID(6666);
            Activitylog.setIPaddress("174.7.7.4");
            Activitylog.setUserActivity("ReadLogByUserNDateTimeLessThan() method is testing...");
            Activitylog.setTimestamp(newTime);
            ULogDAO.WriteLog(Activitylog);

            ULogDM[] log = ULogDAO.ReadLogByUserNDateTimeLessThan(6666, Timestamp.valueOf("2010-10-10 10:10:10.0"));

            ULogDAO.deleteLogByUserNDateTimeLessThan(6666, Timestamp.valueOf("2010-10-10 10:10:10.0"));
            Assert.assertTrue(Activitylog.equals(log[0]));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } finally {
            ULogDAO.closeDbConnection();
        }
    }

    //@Test
    public void Test_ReadLogByUserNDateTimeGreaterThan() {

        ULogDAO ULogDAO = null;
        try {
            ULogDAO = new ULogDAO();

            ULogDM Activitylog = new ULogDM();
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");


            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() + (sec * 1000L));

            Activitylog.setUID(7777);
            Activitylog.setIPaddress("174.7.7.5");
            Activitylog.setUserActivity("ReadLogByUserNDateTimeGreaterThan() method is testing...");
            Activitylog.setTimestamp(newTime);
            ULogDAO.WriteLog(Activitylog);


            ULogDM[] log = ULogDAO.ReadLogByUserNDateTimeGreaterThan(7777, Timestamp.valueOf("2010-10-10 10:10:10.0"));

            ULogDAO.deleteLogByUserNDateTimeGreaterThan(7777, Timestamp.valueOf("2010-10-10 10:10:10.0"));
            Assert.assertTrue(Activitylog.equals(log[0]));
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } finally {
            ULogDAO.closeDbConnection();
        }
    }
}
