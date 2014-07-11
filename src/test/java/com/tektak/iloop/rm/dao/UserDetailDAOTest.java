package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.datamodel.UserDetail;
import org.junit.Assert;
import org.junit.Test;

public class UserDetailDAOTest {
    @Test
    public void testPutUser() throws Exception {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName("Put User Test");
        userDetail.setUserEmail("put_user@testing.com.iloop");
        userDetail.setUserStatus(1);
        userDetail.setUserRole(2);
        Assert.assertEquals(1,1,userDetailDAO.putUser(userDetail));
    }

    @Test
    public void testPrepare() throws Exception {

    }

    @Test
    public void testDmlQuery() throws Exception {
    }

    @Test
    public void testCreateUserTable() throws Exception {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        Assert.assertEquals(-1,-1,userDetailDAO.createUserTable());

    }

    @Test
    public void testUserAuth() throws Exception{
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        Assert.assertEquals(-1,-1,userDetailDAO.userAuth("tes@123.com","test"));
    }
}