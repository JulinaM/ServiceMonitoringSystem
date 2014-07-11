package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.PasswordEnc;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.common.SendEmail;
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
    private ResultSet rs;

    /**
     * Initialize Database connection
     */
    public UserDetailDAO() throws RmException.DBConnectionError, RmodelException.SqlException, RmodelException.CommonException, BaseException.ConfigError {
        DBConnection dbConnection = new DBConnection();
        this.sql = dbConnection.Connect();
        mySqlQuery = new MySqlQuery();

    }

    /**
     * Creates new user and send email to the email-ID of new user
     * Random password is generated and encrypted before storing
     *
     * @param userDetail
     * @return rows affected
     */
    public int putUser(UserDetail userDetail) {
        this.userDetail = userDetail;
        String password = PasswordEnc.createRandomString();
        String encPassword = PasswordEnc.encrypt(userDetail.getUserEmail(), password);
        query = "INSERT INTO %s (userEmail,userName,userPassword,userStatus,userRole,joinDate) VALUES(?,?,?,?,?,?)";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setString(1, userDetail.getUserEmail());
            this.statement.setString(2, userDetail.getUserName());
            this.statement.setString(3, encPassword);
            this.statement.setString(4, userDetail.getUserStatus());
            this.statement.setString(5, userDetail.getUserRole());
            this.statement.setTimestamp(6, new Timestamp(date.getTime()));
            int result = mySqlQuery.Dml();
            if (result == 1) {
                SendEmail.email(userDetail.getUserEmail(), "User Created", "Email: " + userDetail.getUserEmail() + "<br>Password: " + password);
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Initializes prepared statment and sets it to the statement
     *
     * @param query Sql Query passed to be executed
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
     * Read single users detail from database whose userId is provided
     *
     * @param userId Id of user whose details is to be read
     * @return UserDetail type of data if success else null is returned
     */
    public UserDetail fetchUser(int userId) {
        query = "SELECT * FROM %s WHERE userId=?";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setInt(1, userId);
            rs = mySqlQuery.Drl();
            UserDetail detail = new UserDetail();
            while (rs.next()) {
                detail.setUserId(rs.getInt("userId"));
                detail.setUserEmail(rs.getString("userEmail"));
                detail.setUserName(rs.getString("userName"));
                detail.setUserStatus(rs.getString("userStatus"));
                detail.setUserRole(rs.getString("userRole"));
                detail.setJoinDate(rs.getDate("joinDate"));
            }
            return detail;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Read all users detail from database
     *
     * @return UserDetail type of list data if success else null is returned
     */
    public UserDetail[] fetchUser() {
        query = "SELECT * FROM %s";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            rs = mySqlQuery.Drl();
            int numRows = DAOCommon.countRows(rs);
            UserDetail[] list = new UserDetail[numRows];
            int i = 0;
            while (rs.next()) {
                list[i] = new UserDetail();
                list[i].setUserId(rs.getInt("userId"));
                list[i].setUserEmail(rs.getString("userEmail"));
                list[i].setUserName(rs.getString("userName"));
                list[i].setUserStatus(rs.getString("userStatus"));
                list[i].setUserRole(rs.getString("userRole"));
                list[i].setJoinDate(rs.getDate("joinDate"));
                i++;
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public void editUser(int userId) {

    }


    public int removeUser(int userId) throws SQLException, RmodelException.SqlException {
        query = "DELETE FROM %s WHERE userId=?";
        query = String.format(query,TABLE_NAME);
        this.prepare(query);
        this.statement.setInt(1,userId);
        return mySqlQuery.Dml();
    }

    public int removeUser(String email) throws SQLException, RmodelException.SqlException {
        query = "DELETE FROM %s WHERE userEmail=?";
        query = String.format(query,TABLE_NAME);
        this.prepare(query);
        this.statement.setString(1, email);
        return mySqlQuery.Dml();
    }

    /**
     * Closes the preparedstatement and database connection
     */
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
        query = "SELECT * FROM %s WHERE userEmail=? AND userPassword=?";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            String encPassword = PasswordEnc.encrypt(email, password);
            this.statement.setString(1, email);
            this.statement.setString(2, encPassword);
            rs = mySqlQuery.Drl();
            int numRows = DAOCommon.countRows(rs);
            if (numRows == 1) {
                try {
                    rs.next();
                    if (email.equals(rs.getString("userEmail")) && encPassword.equals(rs.getString("userPassword"))) {

                        return 1;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public int checkAvailability(String userEmail) {
        query = "SELECT email FROM %s WHERE userEmail=?";
        query = String.format(query, TABLE_NAME);
        this.prepare(query);
        try {
            this.statement.setString(1, userEmail);
            rs = mySqlQuery.Drl();
            int numRows = DAOCommon.countRows(rs);
            if (numRows == 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
