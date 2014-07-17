package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserActivityLogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;
import org.junit.*;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/8/14.
 */
public class Test_CommonDAO {
    private static UserActivityLogDAO userActivityLogDAO;
    private static UserActivityLogDM Activitylog;
    @BeforeClass
    public static void init(){
        try {
            userActivityLogDAO =new UserActivityLogDAO();
            Activitylog=new UserActivityLogDM();
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
    @AfterClass
    public static void Ainit(){
        userActivityLogDAO.closeDbConnection();
        userActivityLogDAO =null;
    }
    @Before
    public void insertDummyData() throws RmodelException.SqlException, RmodelException.CommonException {
        Activitylog.setUID(3333);
        Activitylog.setIPaddress("170.0.0.1");
        Activitylog.setUserActivity("insertDummyData() method is testing...");
        Timestamp timestamp = Timestamp.valueOf("2010-10-10 10:10:10.0");
        Activitylog.setTimestamp(timestamp);

        userActivityLogDAO.insert(Activitylog);
    }
    @After
    public void delete(){
        try {
            userActivityLogDAO.deleteLogByUser(3333);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void Test_countRows() {
        try {
            MySql mySql = new DBConnection().Connect();
            MySqlQuery mySqlQuery = new MySqlQuery();
            mySqlQuery.setSql(mySql);
            mySqlQuery.setQuery("SELECT * FROM UserActivityLog");
            mySqlQuery.InitPreparedStatement();
            ResultSet rs = mySqlQuery.Drl();

            int RowCount = DAOCommon.countRows(rs);
            Assert.assertTrue(RowCount>0);
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
