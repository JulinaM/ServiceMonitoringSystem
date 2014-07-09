package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.datamodel.UserDetail;
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


public class UserDetailDAO {
    Date date = new Date();
    private UserDetail userDetail;
    private MySql sql;
    private MySqlQuery mySqlQuery;
    private String query;
    private String TABLE_NAME = "userDetail";
    private PreparedStatement statement;

    /**
     *
     */
    public UserDetailDAO() {
        DBConnection dbConnection = new DBConnection();
        this.sql = dbConnection.Connect();
        mySqlQuery = new MySqlQuery();

    }

    /**
     * Adds userDetail in database
     *
     * @param userDetail
     * @return rows affected
     */
    public Integer putUser(UserDetail userDetail) {
        this.userDetail = userDetail;
        String password = null;
        query = "INSERT INTO %s (email,name,password,userStatus,userRole,joinDate) VALUES(?,?,?,?,?,?)";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setString(1, userDetail.getUserEmail());
            this.statement.setString(2, userDetail.getUserName());
            this.statement.setString(3, password);
            this.statement.setInt(4, userDetail.getUserStatus());
            this.statement.setInt(5, userDetail.getUserRole());
            this.statement.setTimestamp(6, new Timestamp(date.getTime()));
            return mySqlQuery.Dml();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param query
     */
    public void prepare(String query) {
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
     * Creates userDetail table if it doesnot exist in the database;
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
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            return mySqlQuery.Dml();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public UserDetail fetchUser(int userId) {
        query = "SELECT * FROM %s WHERE userId=?";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setInt(1, userId);
            ResultSet rs = mySqlQuery.Drl();
            UserDetail detail = new UserDetail();
            while (rs.next()) {
                detail.setUserId(rs.getInt("userId"));
                detail.setUserEmail(rs.getString("email"));
                detail.setUserName(rs.getString("name"));
                detail.setUserStatus(rs.getInt("userStatus"));
                detail.setUserRole(rs.getInt("userRole"));
                detail.setJoinDate(rs.getDate("joinDate"));

            }
            return detail;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDetail[] fetchUser() {

        query = "SELECT * FROM %s";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            ResultSet rs = mySqlQuery.Drl();
            int numRows = DAOCommon.countRows(rs);
            UserDetail[] list = new UserDetail[numRows];
            int i = 0;
            while (rs.next()) {
                list[i] = new UserDetail();
                list[i].setUserId(rs.getInt("userId"));
                list[i].setUserEmail(rs.getString("email"));
                list[i].setUserName(rs.getString("name"));
                list[i].setUserStatus(rs.getInt("userStatus"));
                list[i].setUserRole(rs.getInt("userRole"));
                list[i].setJoinDate(rs.getDate("joinDate"));
                i++;
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void editUser(String userId) {

    }


    public void removeUser(String userId) {

    }

    public void closeConnection() {
        try {
            statement.close();
            mySqlQuery.Close();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int userAuth(String email, String password) throws RmodelException.SqlException, RmodelException.SqlException, RmodelException.CommonException {
        query = "SELECT email,password FROM %s WHERE email=? AND password=?";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setString(1, email);
            this.statement.setString(2, password);
            ResultSet rs = mySqlQuery.Drl();
            int numRows = DAOCommon.countRows(rs);
            if (numRows == 1) {
                try {
                    while (rs.next())
                        if (email.equals(rs.getString("email")) && password.equals(rs.getString("password"))) {
                            return 1;
                        }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int checkAvailability(String userEmail) {
        query = "SELECT email FROM %s WHERE email=?";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setString(1, userEmail);
            ResultSet rs = mySqlQuery.Drl();
            int numRows = DAOCommon.countRows(rs);
            if (numRows == 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
