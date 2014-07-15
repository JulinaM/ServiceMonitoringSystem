package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;
import org.junit.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_ULogDAO {
    private static ULogDAO uLogDAO;
    private static ULogDM Activitylog;
    @BeforeClass
    public static void Init() throws RmException.DBConnectionError, RmodelException.SqlException, RmodelException.CommonException, BaseException.ConfigError {
        uLogDAO = new ULogDAO();
        Activitylog = new ULogDM();
    }
    @AfterClass
    public static void AInit(){
        uLogDAO.closeDbConnection();
        uLogDAO = null;
    }
    @Before
    public void insert() throws RmodelException.SqlException, RmodelException.CommonException {

        Activitylog.setUID(3333);

        Activitylog.setIPaddress("170.0.0.3");
        Activitylog.setUserActivity("method is testing...");
        Timestamp timestamp = Timestamp.valueOf("2010-10-10 10:10:10.0");
        Activitylog.setTimestamp(timestamp);

        uLogDAO.WriteLog(Activitylog);
    }
    @After
    public void delete(){
        try {
            uLogDAO.deleteLogByUser(3333);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void Test_CreateLogTable() {
        try {
            int i = uLogDAO.CreateLogTable();
            if (i == 0)
                Assert.assertTrue(i == 0);
            else if (i == 1)
                Assert.assertTrue(i == 1);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_getRecentLogId() throws RmodelException.SqlException, RmodelException.CommonException {

            System.out.println("Recent Log Id="+uLogDAO.getRecentLogId());

    }

    @Test
    public void Test_WriteLog() {
        ULogDM log = new ULogDM();
        log.setUID(3333);
        log.setIPaddress("170.0.0.2");
        log.setUserActivity("WriteLog() is testing...");
        log.setTimestamp(Timestamp.valueOf("2010-10-10 10:10:10.0"));
        try {
            uLogDAO.WriteLog(log);
            ULogDM[] uLogDMs=uLogDAO.ReadAllLog();
            Assert.assertTrue(log.equals(uLogDMs[1]));
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_fetchLog() {
        try {
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
        }
    }

    @Test
    public void Test_ReadAllLog() {

        try {
            ULogDM[] log = uLogDAO.ReadAllLog();
            Assert.assertTrue(Activitylog.equals(log[0]));
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_ReadLogByUser() {

        try {
            ULogDM[] log = uLogDAO.ReadLogByUser(3333);

            Assert.assertTrue(Activitylog.equals(log[0]));

        }catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_ReadLogByDateTimeGreaterThan() {
        try {
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");
            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() + (sec * 1000L));
            Activitylog.setTimestamp(newTime);
            int r=uLogDAO.WriteLog(Activitylog);
            ULogDM[] log = uLogDAO.ReadLogByDateTimeGreaterThan(Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(Activitylog.equals(log[1]));
        }catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_ReadLogByDateTimeLessThan() {

        try {
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");

            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() - (sec * 1000L));

            Activitylog.setTimestamp(newTime);

            uLogDAO.WriteLog(Activitylog);

            ULogDM[] log = uLogDAO.ReadLogByDateTimeLessThan(Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(Activitylog.equals(log[1]));
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_ReadLogByUserNDateTimeLessThan() {

        try {
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");
            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() - (sec * 1000L));

            Activitylog.setTimestamp(newTime);
            uLogDAO.WriteLog(Activitylog);

            ULogDM[] log = uLogDAO.ReadLogByUserNDateTimeLessThan(3333, Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(Activitylog.equals(log[1]));
        }catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_ReadLogByUserNDateTimeGreaterThan() {


        try {
            int sec = 0;
            Timestamp original = Timestamp.valueOf("2010-10-10 10:10:10.0");
            sec = 60;
            Timestamp newTime = new Timestamp(original.getTime() + (sec * 1000L));

            Activitylog.setTimestamp(newTime);
            uLogDAO.WriteLog(Activitylog);

            ULogDM[] log = uLogDAO.ReadLogByUserNDateTimeGreaterThan(3333, Timestamp.valueOf("2010-10-10 10:10:10.0"));

            Assert.assertTrue(Activitylog.equals(log[1]));
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
}
