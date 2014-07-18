package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.common.ServletCommon;
import com.tektak.iloop.rm.datamodel.UserActivityLogDM;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rm.datamodel.UserRole;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;
import org.json.JSONObject;
import org.junit.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_UserActivityLogDAO {
    private static UserActivityLogDAO dbLog;
    private static UserActivityLogDM logDM;
    private static UserDetail uDetail;
    private static UserDetailDAO dbUDetail;

    @BeforeClass
    public static void Init() throws RmException.DBConnectionError, RmodelException.SqlException, RmodelException.CommonException, BaseException.ConfigError {
        dbLog = new UserActivityLogDAO();
        logDM = new UserActivityLogDM();
        uDetail=new UserDetail();
        dbUDetail=new UserDetailDAO();
    }

    @AfterClass
    public static void AInit() {
        dbLog.closeDbConnection();
        dbLog = null;
    }

    @Before
    public void insertDummyData() throws RmodelException.SqlException, RmodelException.CommonException {
uDetail.setUserName("tektak");
        uDetail.setUserRole("[AddUser, UpdateUser, DeleteUser, DeleteLog]");
        uDetail.setUserEmail("anil.bhaila@outlook.com");
        uDetail.setUserPassword("pass");
        uDetail.setUserStatus("1");

        try {
            dbUDetail.putUser(uDetail);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logDM.setUID(dbUDetail.getRecentUserId());
        logDM.setIPaddress("170.0.0.1");
        logDM.setUserActivity("insertDummyData() is testing...");
        Timestamp timestamp = Timestamp.valueOf("2010-10-10 10:10:10.0");
        logDM.setTimestamp(timestamp);

        dbLog.insert(logDM);
    }

    @After
    public void delete() {
        try {
            dbLog.deleteLogByUser(3333);
            dbLog.deleteLogByUser(4444);
            dbUDetail.removeUser(dbUDetail.getRecentUserId());
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_CreateLogTable() {
        try {
            int i = dbLog.CreateLogTable();
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
        System.out.println("Recent Log Id=" + dbLog.getRecentLogId());
    }

    @Test
    public void Test_insert() {
        UserActivityLogDM log = new UserActivityLogDM();
        log.setUID(3333);
        log.setIPaddress("170.0.0.0");
        log.setUserActivity("insert() is testing...");
        log.setTimestamp(Timestamp.valueOf("2010-10-10 10:10:10.0"));
        try {
            dbLog.insert(log);
            UserActivityLogDM[] userActivityLogDMs = dbLog.selectByLogId(dbLog.getRecentLogId());
            Assert.assertTrue(log.equals(userActivityLogDMs[0]));

        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_fillLog() {
        try {
            logDM.setUID(4444);
            logDM.setIPaddress("170.0.0.0");
            logDM.setUserActivity("fillLog() is testing...");
            logDM.setTimestamp(Timestamp.valueOf("2010-10-10 10:10:10.0"));
            dbLog.insert(logDM);

            MySqlQuery mySqlQuery = new MySqlQuery();
            mySqlQuery.setSql(new DBConnection().Connect());
            mySqlQuery.setQuery("SELECT UId, IPaddress, Activity, Timestamp FROM UserActivityLog WHERE UId=?");
            mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, 4444);

            ResultSet rs = mySqlQuery.Drl();
            UserActivityLogDM[] logFetched=dbLog.fillLog(rs);

            Assert.assertTrue(logDM.equals(logFetched[0]));

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
    public void Test_selectAllLog() {
        try {
            UserActivityLogDM[] log = dbLog.selectAllLog();
            Assert.assertTrue(log.length > 0);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_selectLogByUser() {
        try {
            UserActivityLogDM[] log = dbLog.selectLogByUser(dbUDetail.getRecentUserId());
            Assert.assertTrue(logDM.equals(log[0]));

        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
}

