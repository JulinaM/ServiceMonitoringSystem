package com.tektak.iloop.rm.model;

import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.connection.MySqlConnection;
import com.tektak.iloop.rmodel.driver.MySql;

/**
 * Created by tektak on 7/3/14.
 */
public class DBConnection {

    public MySql Connect(){
        MySqlConnection connection = new MySqlConnection();
        connection.setDatabaseName("rmmodel");
        connection.setDriver("com.mysql.jdbc.Driver");
        connection.setUrl("jdbc:mysql://10.0.3.107:3306/");
        connection.setUsername("root");
        connection.setPassword("tektak");

        MySql mySql = new MySql();
        mySql.setConnection(connection);
        try {
            mySql.InitConnection();

        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return mySql;
    }
}
