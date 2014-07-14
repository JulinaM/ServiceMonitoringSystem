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
        userDetail.setUserEmail("put_user@testing.com.ilooprm");
        userDetail.setUserPassword("putTest");
        userDetail.setUserStatus("1");
        userDetail.setUserRole("2");
        Assert.assertEquals(1, userDetailDAO.putUser(userDetail));
        userDetailDAO.removeUser(userDetail.getUserEmail());
    }

    @Test
    public void testEditUser() throws Exception {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName("Auth Test");
        userDetail.setUserEmail("authtest@testing.com.ilooprm");
        userDetail.setUserPassword("authTest");
        userDetail.setUserStatus("1");
        userDetailDAO.putUser(userDetail);
        UserDetail list  = userDetailDAO.fetchUser(userDetail.getUserEmail());
        Assert.assertEquals(1, userDetailDAO.editUser(list));
        userDetailDAO.removeUser(list.getUserId());
    }

    @Test
    public void testUserAuth() throws Exception {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName("Auth Test");
        userDetail.setUserEmail("authtest@testing.com.ilooprm");
        userDetail.setUserPassword("authTest");
        userDetail.setUserStatus("1");
        userDetailDAO.putUser(userDetail);
        Assert.assertEquals(1, userDetailDAO.userAuth(userDetail.getUserEmail(), userDetail.getUserPassword()));
        userDetailDAO.removeUser(userDetail.getUserEmail());
    }
}