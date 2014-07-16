package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.ULogDM;
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
    private static ULogDAO uLogDAO;
    private static ULogDM Activitylog;
    @BeforeClass
    public static void init(){
        try {
            uLogDAO=new ULogDAO();
            Activitylog=new ULogDM();
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
        uLogDAO.closeDbConnection();
        uLogDAO=null;
    }
    @Before
    public void insert() throws RmodelException.SqlException, RmodelException.CommonException {

        Activitylog.setUID(3333);

        Activitylog.setIPaddress("170.0.0.3");
        Activitylog.setUserActivity("Fetchlog() method is testing...");
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
    public void Test_countRows() {
        try {
            MySql mySql = new DBConnection().Connect();
            MySqlQuery mySqlQuery = new MySqlQuery();
            mySqlQuery.setSql(mySql);
            mySqlQuery.setQuery("SELECT * FROM UserActivityLog");
            mySqlQuery.InitPreparedStatement();
            ResultSet rs = mySqlQuery.Drl();

            int RowCount = DAOCommon.countRows(rs);

            Assert.assertEquals(RowCount, 1);
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
