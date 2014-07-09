package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

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
    public UserDetailDAO() throws RmException.DBConnectionError, RmodelException.SqlException, RmodelException.CommonException, BaseException.ConfigError {
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
            return dmlQuery();
        } catch (SQLException e) {
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



    /**
     * This method creates UserDetailDetail table if it doesnot exist in the databse;
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
        return dmlQuery();
    }

    public UserDetail fetchUser(int userId) {
        query = "SELECT * FROM %s WHERE userId=?";
        query = String.format(query,TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setInt(1,userId);
            ResultSet rs = drlQuery(query);

            while (rs.next()){
                this.userDetail.setUserId(rs.getInt("userId"));
                this.userDetail.setUserEmail(rs.getString("email"));
                this.userDetail.setUserName(rs.getString("name"));
                this.userDetail.setUserStatus(rs.getInt("userStatus"));
                this.userDetail.setUserRole(rs.getInt("userRole"));
                this.userDetail.setJoinDate(rs.getDate("joinDate"));

            }
            return this.userDetail;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDetail[] fetchUser() {

        query = "SELECT * FROM %s";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        ResultSet rs = drlQuery(query);

        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = drlQuery(query);
        int numRows = DAOCommon.countRows(rs);
        if (numRows == 1) {
            try {
                while (rs.next())
                    if (email.equals(rs.getString("email")) && password.equals(rs.getString("password"))) {
                        return 1;
                    } else {
                        return -1;
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return -1;

    }
}
