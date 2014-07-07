package com.tektak.iLoop.Rm.DAO;

import com.tektak.iLoop.Rm.Common.DateTime;
import com.tektak.iLoop.Rm.Common.RmException;
import com.tektak.iLoop.Rm.Datamodel.UserActivityLogDataModel;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by tektak on 7/7/14.
 */
public class userActivityLogGeneratorDAO_Test {
    //@Test
    public void Test_CreateLogTable(){

        try {

            new userActivityLogDAO().CreateLogTable();
            System.out.println("Test_CreateLogTable()");
            new userActivityLogDAO().closeDbConnection();

        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }
    //@Test
    public void Test_WriteLog(){
        UserActivityLogDataModel log=new UserActivityLogDataModel();
        log.setUID(101);
        log.setIPaddress("192.168.1.100");
        log.setUserActivity("Loging system test....");

        log.setTimestamp(DateTime.getTimestamp());
        int insertedRows= 0;
        try {
            insertedRows = new userActivityLogDAO().WriteLog(log);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        System.out.println("=====================================================");
        System.out.println("Inserted Rows::"+insertedRows);
        System.out.println("=====================================================");
    }

    @Test
    public void Test_ReadLog(){
        try {
            userActivityLogDAO userActivityLogDAO =new userActivityLogDAO();
            UserActivityLogDataModel[] log=userActivityLogDAO.ReadLog();
            System.out.println("=====================================================");
            for(int i=0;i<log.length;i++){
                System.out.println("UId::"+log[i].getUID());
                System.out.println("IPaddress::"+log[i].getIPaddress());
                System.out.println("UserActivity::"+log[i].getUserActivity());
                System.out.println("Timestamp::"+log[i].getTimestamp());
                System.out.println("=====================================================");
            }

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
