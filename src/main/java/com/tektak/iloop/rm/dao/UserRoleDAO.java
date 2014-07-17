package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserRole;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tektak on 7/17/14.
 */
public class UserRoleDAO {
    private String TABLE_NAME = "UserRole";
    /**
     * Store MySqlQuery operation
     */
    private MySqlQuery mySqlQuery;

    public UserRoleDAO() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery = new MySqlQuery();
        this.mySqlQuery.setSql(new DBConnection().Connect());
    }

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

    public UserRole select(int userId) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "SELECT (userId,userRole) FROM %s WHERE userId = ? ";
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

    public UserRole fillUserRole(ResultSet rs) throws RmodelException.SqlException {
        UserRole userRole=new UserRole();
        try {
            userRole.setUserId(rs.getInt("userId"));
            userRole.setUserRole(rs.getString("userRole"));
            return userRole;
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }

    }

    public void closeDbConnection(){
        try {
            this.mySqlQuery.Close();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
    }

}
