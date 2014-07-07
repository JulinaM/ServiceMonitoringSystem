package com.tektak.iLoop.Rm.Common;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Test;

/**
 * Created by tektak on 7/7/14.
 */
public class DBConnection_Test {
    @Test
    public void Test_getMySql(){
        DBConnection db=new DBConnection();
        MySql mySql= null;
        try {
            mySql = db.getMySql();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
        if(mySql==null){
            System.out.println("mySql==null");
        }else{
            System.out.println("===================================================================");
            System.out.println("Mysql Connection Successful!!");
            System.out.println("===================================================================");
        }
    }
}
