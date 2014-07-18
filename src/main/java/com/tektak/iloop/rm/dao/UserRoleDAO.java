package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserRole;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tektak on 7/17/14.
 */
public class UserRoleDAO {
    /**
     * Store database table name
     */
    private String TABLE_NAME = "UserRole";
    /**
     * Store MySqlQuery operation
     */
    private MySqlQuery mySqlQuery;

    /**
     * UserRoleDAO constructor
     * Creates and initialize MySqlQuery object mySqlQuery
     * @throws RmException.DBConnectionError
     * @throws BaseException.ConfigError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public UserRoleDAO() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery = new MySqlQuery();
        this.mySqlQuery.setSql(new DBConnection().Connect());
    }

    /**
     * Method to insert User Role in data base
     * @param userRole
     * @return integer value to indicate no. of inserted rows
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int insert(UserRole userRole) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "INSERT INTO %s (userId,userRole) VALUES(?,?)";
            query = String.format(query, TABLE_NAME);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, userRole.getUserId());
            ps.setString(2, userRole.getUserRole());
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to read User Role from database
     * @param userId
     * @return Datamodel of UserRole
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public UserRole select(int userId) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "SELECT userId,userRole FROM %s WHERE userId = ? ";
            query = String.format(query, TABLE_NAME);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, userId);
            ResultSet rs= this.mySqlQuery.Drl();
            return this.fillUserRole(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to update User Role
     * @param userId
     * @param userRole
     * @return integer value to indicate no. of updated rows
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int update(int userId, String userRole) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "UPDATE %s SET userRole = ? WHERE userId = ? ";
            query = String.format(query, TABLE_NAME);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setString(1, userRole);
            ps.setInt(2, userId);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to delete User Role
     * @param userId
     * @return returns no. of deleted rows
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int delete(int userId) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "DELETE FROM %s WHERE userId = ? ";
            query = String.format(query, TABLE_NAME);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, userId);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to fill result set of data base to data model of UserRole
     * @param rs
     * @return returns object of UserRole data model
     * @throws RmodelException.SqlException
     */
    public UserRole fillUserRole(ResultSet rs) throws RmodelException.SqlException {
        UserRole userRole=new UserRole();
        try {
            rs.next();
            userRole.setUserId(rs.getInt("userId"));
            userRole.setUserRole(rs.getString("userRole"));
            return userRole;
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }

    }

    /**
     * Method to close Db connection of MysqlQuery object
     */
    public void closeDbConnection(){
        try {
            this.mySqlQuery.Close();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
    }

}
