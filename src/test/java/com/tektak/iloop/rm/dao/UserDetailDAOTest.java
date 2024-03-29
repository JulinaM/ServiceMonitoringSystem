package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class UserDetailDAOTest {
    private static UserDetailDAO userDetailDAO;
    private static UserDetail userDetail;

    @BeforeClass
    public static void init() {
        try {
            userDetailDAO = new UserDetailDAO();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        userDetail = new UserDetail();
    }

    @AfterClass
    public static void close() {
        userDetailDAO.closeConnection();
        userDetail = null;
    }

    @Test
    public void testPutUser() throws RmodelException.SqlException, SQLException, RmodelException.CommonException {
        userDetail.setUserName("abc");
        userDetail.setUserEmail("abc@abc.com");
        userDetail.setUserPassword("abc");
        userDetail.setUserStatus("1");
        userDetail.setUserRole("[AddUser, UpdateUser, DeleteUser, DeleteLog]");
        Assert.assertEquals(1, userDetailDAO.putUser(userDetail));
        userDetailDAO.removeUser(userDetail.getUserEmail());
    }

    @Test
    public void testEditUser() throws SQLException, RmodelException.SqlException, RmodelException.CommonException {
        userDetail.setUserName("Auth Test");
        userDetail.setUserEmail("authtest@testing.com.ilooprm");
        userDetail.setUserPassword("authTest");
        userDetail.setUserStatus("1");
        userDetailDAO.putUser(userDetail);
        int id = (userDetailDAO.fetchUser(userDetail.getUserEmail())).getUserId();
        userDetail.setUserId(id);
        userDetail.setUserName("Edit Test");
        userDetail.setUserEmail("edittest@testing.com.ilooprm");
        Assert.assertEquals(1, userDetailDAO.editUser(userDetail));
        UserDetail list = userDetailDAO.fetchUser(userDetail.getUserId());
        Assert.assertEquals(userDetail.getUserEmail(), list.getUserEmail());
        Assert.assertEquals(userDetail.getUserName(), list.getUserName());
        userDetailDAO.removeUser(userDetail.getUserId());
    }

    @Test
    public void testUserAuth() throws RmodelException.SqlException, SQLException, RmodelException.CommonException {
        userDetail.setUserName("Auth Test");
        userDetail.setUserEmail("authtest@testing.com.ilooprm");
        userDetail.setUserPassword("authTest");
        userDetail.setUserStatus("1");
        userDetailDAO.putUser(userDetail);
        Assert.assertEquals(1, userDetailDAO.userAuth(userDetail.getUserEmail(), userDetail.getUserPassword()));
        userDetailDAO.removeUser(userDetail.getUserEmail());
    }
}