package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserRole;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.junit.*;

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
            userRole.delete(9999);
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
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test_insert(){

        try {
            userRole.insert(urDM);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }

}
