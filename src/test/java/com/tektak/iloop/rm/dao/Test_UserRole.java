package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserRole;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;
import org.junit.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tektak on 7/17/14.
 */
public class Test_UserRole {
    private static UserRole urDM = null;
    private static UserRoleDAO userRole=null;
    @BeforeClass
    public static void init(){
        try {
            urDM=new UserRole();
            userRole=new UserRoleDAO();
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
    public static void Aint(){
        userRole.closeDbConnection();
    }
    @Before
    public void insertDummyData(){
        try {
            urDM.setUserId(9999);
            urDM.setUserRole("login");
            userRole.insert(urDM);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteDummyDate(){
        try {
            userRole.delete(9999);
            userRole.delete(8888);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_insert(){
        try {
            urDM.setUserId(8888);
            urDM.setUserRole("add user");
            userRole.insert(urDM);

            UserRole actual=userRole.select(8888);

            Assert.assertEquals(urDM.getUserRole(),actual.getUserRole());
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_update(){
        try {
            userRole.update(9999,"update previlage");
            UserRole actual=userRole.select(9999);

            Assert.assertEquals("update previlage",actual.getUserRole());
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_delete(){
        try {
            int i=userRole.delete(9999);
            Assert.assertTrue(i==1);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void Test_fillUser() throws SQLException, RmodelException.SqlException {
        MySqlQuery mySqlQuery=new MySqlQuery();
        try {
            String query = "SELECT userId,userRole FROM %s WHERE userId = ? ";
            query = String.format(query, "UserRole");

            mySqlQuery.setSql(new DBConnection().Connect());
            mySqlQuery.setQuery(query);
            mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, 9999);
            ResultSet rs= mySqlQuery.Drl();
            urDM=userRole.fillUserRole(rs);

            Assert.assertEquals("login",urDM.getUserRole());
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            mySqlQuery.Close();
        }

    }

}
