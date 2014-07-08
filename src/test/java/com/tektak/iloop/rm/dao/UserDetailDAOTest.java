package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.datamodel.UserDetail;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserDetailDAOTest {
    UserDetailDAO userDetailDAO = new UserDetailDAO();

    @Test
    public void testPutUser() throws Exception {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName("Sanjaya");
        userDetail.setUserEmail("sanjaya.khatri@tektak.com");
        userDetail.setUserStatus(1);
        userDetail.setUserRole(2);
        Assert.assertNotNull(userDetailDAO.putUser(userDetail));
    }

    @Test
    public void testPrepare() throws Exception {

    }

    @Test
    public void testDmlQuery() throws Exception {
    }

    @Test
    public void testCreateUserTable() throws Exception {
        Assert.assertNotNull(userDetailDAO.createUserTable());

    }

    @Test
    public void testUserAuth() throws Exception{
        Assert.assertNotNull(userDetailDAO.userAuth("tes@123.com","test"));

    }


}