package com.tektak.iloop.rm.common;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.connection.MySqlConnection;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.Config;

import java.net.URL;

/**
 * Created by tektak on 7/4/14.
 */
public class DBConnection {
    /**
     * Create MySql object
     *
     * @return MySql object
     */
    public MySql Connect() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException {
        ClassLoader classLoader = DBConnection.class.getClassLoader();
        URL url = classLoader.getResource("configuration.properties");
        CommonConfig commonCommonConfig = new CommonConfig(url);
        Config config = commonCommonConfig.getConfig();

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
