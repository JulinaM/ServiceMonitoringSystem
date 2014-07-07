package com.tektak.iLoop.Rm.Application.loginSystem;

import com.tektak.iLoop.Rm.Common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;

import java.sql.SQLException;

/**
 * Created by tektak on 7/7/14.
 */
public class AuthenticateUser_Test {
    //@Test
    public void Test_getAuthenticateUser(){
        AuthenticateUser Au= null;
        try {
            Au = new AuthenticateUser();
            Au.IsAuthenticateUser("test@123.com", "test");
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
    }
}
