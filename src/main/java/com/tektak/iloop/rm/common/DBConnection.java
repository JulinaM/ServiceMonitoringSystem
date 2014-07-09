package com.tektak.iloop.rm.common;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.connection.MySqlConnection;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.util.common.BaseException;


/**
 * Created by tektak on 7/4/14.
 */
public class DBConnection {
    /**
     * Create MySql object
     * @return MySql object
     */
    public MySql getMySql() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException{
        CommonConfig commonCommonConfig =new CommonConfig();
        com.tektak.iloop.util.configuration.Config config= commonCommonConfig.getConfig();

        MySqlConnection mySqlConnection = new MySqlConnection();
        mySqlConnection.setDatabaseName(config.ReadString("dbName"));
        mySqlConnection.setUsername(config.ReadString("username"));
        mySqlConnection.setPassword(config.ReadString("password"));
        mySqlConnection.setUrl(config.ReadString("url"));
        mySqlConnection.setDriver(config.ReadString("driver"));

        MySql mySql = new MySql();
        mySql.setConnection(mySqlConnection);
        mySql.InitConnection();
        return mySql;
    }
}
