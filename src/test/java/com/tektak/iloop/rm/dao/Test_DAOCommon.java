package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

import java.sql.ResultSet;

/**
 * Created by tektak on 7/8/14.
 */
public class Test_DAOCommon {
    //@Test
    public void Test_countRows(){
        try {
            MySql mySql=new DBConnection().getMySql();
            MySqlQuery mySqlQuery=new MySqlQuery();
            mySqlQuery.setSql(mySql);
            mySqlQuery.setQuery("SELECT * FROM UserActivityLog");
            mySqlQuery.InitPreparedStatement();
            ResultSet rs=mySqlQuery.Drl();
            mySqlQuery.Close();
            int RowCount= DAOCommon.countRows(rs);
            System.out.println("No. of Rows returned::"+RowCount);
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
}
