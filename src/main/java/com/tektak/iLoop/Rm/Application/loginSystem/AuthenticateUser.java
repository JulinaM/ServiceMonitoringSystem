package com.tektak.iLoop.Rm.Application.loginSystem;

import com.tektak.iLoop.Rm.Common.DBConnection;
import com.tektak.iLoop.Rm.Common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tektak on 7/4/14.
 */
public class AuthenticateUser {
    private MySql mySql;

    /**
     * Constructor to instantiate mysql connection
     * @throws RmException.DBConnectionError
     * @throws BaseException.ConfigError
     * @throws RmodelException.SqlException
     */
    public AuthenticateUser() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException {
        this.mySql=new DBConnection().getMySql();
    }

    /**
     * check authenticate user in database
     * @param email
     * @param password
     * @return
     * @throws RmodelException.SqlException
     * @throws SQLException
     * @throws RmodelException.CommonException
     */
    public int IsAuthenticateUser(String email,String password) throws RmodelException.SqlException, SQLException, RmodelException.CommonException {

            MySqlQuery mySqlQuery=new MySqlQuery();

            String query = "SELECT  email,password FROM userDetail WHERE email = ? AND password=?";

            mySqlQuery.setSql(this.mySql);
            mySqlQuery.setQuery(query);
            mySqlQuery.InitPreparedStatement();

            PreparedStatement st=mySqlQuery.getPreparedStatement();
            st.setString(1, email);
            st.setString(2, password);
            ResultSet resultSet=mySqlQuery.Drl();
            this.mySql.CloseConnection();
            if(resultSet == null)
                return 0;
            else {
                while(resultSet.next()) {
                    System.out.println("email::"+resultSet.getString("email"));
                    System.out.println("password::"+resultSet.getString("password"));
                }
                return 1;
            }


    }
}
