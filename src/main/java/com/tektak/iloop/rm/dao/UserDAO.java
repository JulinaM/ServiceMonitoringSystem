package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.model.DBConnection;
import com.tektak.iloop.rm.model.IUser;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */


public class UserDAO implements IUserDAO {
    Date date = new Date();
    private IUser user;
    private MySql sql;
    private MySqlQuery mySqlQuery;
    private String query;
    private String TABLE_NAME = "userDetail";

    /**
     *
     */
    public UserDAO() {
        DBConnection dbConnection = new DBConnection();
        this.sql = dbConnection.Connect();

    }

    /**
     * @param user
     */
    @Override
    public Integer putUser(IUser user) {
        this.user = user;
        String password = null;
        query = "INSERT INTO " + TABLE_NAME + " (email,name,password,userStatus,userRole,joinDate) " +
                "values('" + user.getUserEmail() + "','" + user.getUserName() + "'," + password + "," +
                "'" + user.getUserStatus() + "','" + user.getUserRole() + "','" + new Timestamp(date.getTime()) + "')";
        return dmlQuery(query);

    }

    @Override
    public void fetchUser(String userId) {


    }

    public Integer dmlQuery(String query) {
        try {
            mySqlQuery = new MySqlQuery(this.sql, query);
            return mySqlQuery.Dml();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public ResultSet drlQuery(String query) {
        try {
            mySqlQuery = new MySqlQuery(this.sql, query);
            return mySqlQuery.Drl();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * This method creates User table if it doesnot exist in the databse;
     *
     * @return
     */
    public Integer createUserTable() {
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                "userId int(20) PRIMARY KEY AUTO_INCREMENT, " +
                "email varchar(150), " +
                "name varchar(150), " +
                "password varchar(250), " +
                "userStatus int(1)," +
                "userRole int(1), " +
                "joinDate timestamp)";
        return dmlQuery(query);
    }

    public ResultSet fetchUser() {
        query = "SELECT * FROM " + TABLE_NAME;
        System.out.println(query);
        return drlQuery(query);

    }

    @Override
    public void editUser(String userId) {

    }

    @Override
    public void removeUser(String userId) {

    }
}
