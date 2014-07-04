package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.model.User;
import org.junit.Assert;
import org.junit.Test;

public class UserDAOTest {
    UserDAO userDAO = new UserDAO();

    @Test
    public void testPutUser() throws Exception {
        User user = new User();
        user.setUserName("Sanjaya");
        user.setUserEmail("sanjaya.khatri@tektak.com");
        user.setUserStatus(1);
        user.setUserRole(2);
        Assert.assertNotNull(userDAO.putUser(user));
    }

    @Test
    public void testPrepare() throws Exception {

    }

    @Test
    public void testDmlQuery() throws Exception {
        Assert.assertNotNull(userDAO.dmlQuery());
    }

    @Test
    public void testCreateUserTable() throws Exception {
        Assert.assertNotNull(userDAO.createUserTable());

    }


}