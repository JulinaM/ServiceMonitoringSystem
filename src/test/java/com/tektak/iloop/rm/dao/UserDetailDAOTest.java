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
        userDetail.setUserStatus("1");
        userDetail.setUserRole("2");
        if (userDetailDAO.checkAvailability(userDetail.getUserEmail()) == 1) {
            Assert.assertEquals(1, userDetailDAO.putUser(userDetail));
            Assert.assertEquals(1,userDetailDAO.removeUser(userDetail.getUserEmail()));
            Assert.assertEquals(0,userDetailDAO.removeUser(userDetail.getUserEmail()));
        }
    }
    @Test
    public void testPrepare() throws Exception {

    }

    @Test
    public void testDmlQuery() throws Exception {
    }

    @Test
    public void testUserAuth() throws Exception{
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        Assert.assertEquals(1,userDetailDAO.userAuth("test@123.com","test"));
        Assert.assertEquals(-1,userDetailDAO.userAuth("tes@123.com","test"));
    }
}