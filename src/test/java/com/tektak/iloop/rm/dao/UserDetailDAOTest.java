package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.datamodel.UserDetail;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserDetailDAOTest {
    //@Test
    public void testPutUser() throws Exception {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName("Sanjaya");
        userDetail.setUserEmail("sanjaya.khatri@tektak.com");
        userDetail.setUserStatus(1);
        userDetail.setUserRole(2);
        Assert.assertNotNull(userDetailDAO.putUser(userDetail));
    }

    //@Test
    public void testPrepare() throws Exception {

    }

    //@Test
    public void testDmlQuery() throws Exception {
    }

    //@Test
    public void testCreateUserTable() throws Exception {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        Assert.assertNotNull(userDetailDAO.createUserTable());

    }

    //@Test
    public void testUserAuth() throws Exception{
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        Assert.assertNotNull(userDetailDAO.userAuth("tes@123.com","test"));
    }
}