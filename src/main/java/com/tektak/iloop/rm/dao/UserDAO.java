package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.model.DBConnection;
import com.tektak.iloop.rm.model.IUser;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */


public class UserDAO {
    private IUser user;
    private MySql sql;
    private MySqlQuery mySqlQuery;
    private String query;
    private String TABLE_NAME = "userDetail";
    private PreparedStatement statement;
    Date date = new Date();

    /**
     *
     */
    public UserDAO() {
        DBConnection dbConnection = new DBConnection();
        this.sql = dbConnection.Connect();
        mySqlQuery = new MySqlQuery();

    }

    /**
     * Adds user in database
     * @param user
     * @return rows affected
     */
    public Integer putUser(IUser user) {
        this.user = user;
        String password = null;
        query = "INSERT INTO %s (email,name,password,userStatus,userRole,joinDate) VALUES(?,?,?,?,?,?)";
        query = String.format(query,TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setString(1,user.getUserEmail());
            this.statement.setString(2,user.getUserName());
            this.statement.setString(3,password);
            this.statement.setInt(4, user.getUserStatus());
            this.statement.setInt(5, user.getUserRole());
            this.statement.setTimestamp(6, new Timestamp(date.getTime()));
            return dmlQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *
     * @param query
     */
    public void prepare(String query){
        mySqlQuery.setQuery(query);
        mySqlQuery.setSql(this.sql);
        try {
            mySqlQuery.InitPreparedStatement();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
        this.statement = mySqlQuery.getPreparedStatement();
    }

    /**
     *
     * @return
     */
    public Integer dmlQuery() {
        try {
            return mySqlQuery.Dml();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param query
     * @return
     */
    public ResultSet drlQuery(String query) {
        try {
            return mySqlQuery.Drl();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void fetchUser(String userId) {


    }





    /**
     * This method creates User table if it doesnot exist in the databse;
     *
     * @return
     */
    public Integer createUserTable() {
        query = "CREATE TABLE IF NOT EXISTS %s ( " +
                "userId int(20) PRIMARY KEY AUTO_INCREMENT, " +
                "email varchar(150), " +
                "name varchar(150), " +
                "password varchar(250), " +
                "userStatus int(1)," +
                "userRole int(1), " +
                "joinDate timestamp)";
        query = String.format(query,TABLE_NAME);
        this.prepare(query);
        return dmlQuery();
    }

    public ResultSet fetchUser() {
        query = "SELECT * FROM %s";
        query = String.format(query,TABLE_NAME);
        this.prepare(query);
        ResultSet rs = drlQuery(query);
        try {
            System.out.println(rs.first());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            while (rs.next()){

                System.out.println(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public void editUser(String userId) {

    }


    public void removeUser(String userId) {

    }

    public void closeConnection(){
        try {
            mySqlQuery.Close();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
    }
}
