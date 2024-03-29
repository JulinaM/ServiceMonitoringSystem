package com.tektak.iloop.rm.common;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_DBConnection {
    @Test
    public void Test_Connect() throws IOException {
        DBConnection db=new DBConnection();
        MySql mySql= null;
        try {
            mySql = db.Connect();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            try {
                mySql.CloseConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }
        Assert.assertNotNull("Mysql Connection Unsuccessful!!",mySql);
        Assert.assertTrue(mySql!=null);
    }
}
