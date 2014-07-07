package com.tektak.iLoop.Rm.DAO;

import com.tektak.iLoop.Rm.Common.DBConnection;
import com.tektak.iLoop.Rm.Common.RmException;
import com.tektak.iLoop.Rm.Datamodel.UserActivityLogDataModel;
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
public class userActivityLogDAO {
    /**
     * Store mysql connection
     */
    private MySql mySql;
    /**
     * Store MySqlQuery operation
     */
    private MySqlQuery mySqlQuery;

    /**
     * Constructor to instantiate DBConnection and getMySql object
     * @throws RmException.DBConnectionError
     * @throws BaseException.ConfigError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public userActivityLogDAO() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException {
         this.mySql=new DBConnection().getMySql();
         this.mySqlQuery=new MySqlQuery();
         this.mySqlQuery.setSql(this.mySql);
    }

    /**
     * Method to Create UserActivityLog Table if not exist.
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public void CreateLogTable() throws RmodelException.SqlException, RmodelException.CommonException {
        if(mySqlQuery!=null&&mySql!=null) {
            this.mySqlQuery.setQuery("create table if not exists UserActivityLog( logId int(11) auto_increment, UId int(11) not null,IPaddress varchar(50),Activity varchar(255),Timestamp timestamp, primary key(logId) ); ");
            this.mySqlQuery.InitPreparedStatement();
            this.mySqlQuery.Dml();
            System.out.println("-------------"+this.mySqlQuery.getQuery());
        }
    }

    /**
     * Method to Insert log data in Mysql Database
     * @param log
     * @return  integer value for rows inserted
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     * @throws SQLException
     */
    public int WriteLog(UserActivityLogDataModel log) throws RmodelException.SqlException, RmodelException.CommonException, SQLException {
        if(mySqlQuery!=null&&mySql!=null) {
            this.mySqlQuery.setQuery("INSERT INTO UserActivityLog (UId,IPaddress,Activity,Timestamp) VALUES(?,?,?,?)");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();

            ps.setInt(1, log.getUID());
            ps.setString(2, log.getIPaddress());
            ps.setString(3, log.getUserActivity());
            ps.setTimestamp(4, log.getTimestamp());

            return this.mySqlQuery.Dml();
        }
        return 0;
    }

    /**
     * Method to Read User Activity Log information
     * @return  UserActivityLogDataModel type object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     * @throws SQLException
     */
    public UserActivityLogDataModel[] ReadLog() throws RmodelException.SqlException, RmodelException.CommonException, SQLException {
          this.mySqlQuery.setQuery("SELECT * FROM UserActivityLog");
          this.mySqlQuery.InitPreparedStatement();
          ResultSet rs=this.mySqlQuery.Drl();
          int RowCount=this.getNumberRows(rs);
          UserActivityLogDataModel[] ActivityLog=new UserActivityLogDataModel[RowCount];
          int i=0;
          while(rs.next()) {
              ActivityLog[i]=new UserActivityLogDataModel();
              ActivityLog[i].setUID(rs.getInt("UId"));
              ActivityLog[i].setIPaddress(rs.getString("IPaddress"));
              ActivityLog[i].setUserActivity(rs.getString("Activity"));
              ActivityLog[i].setTimestamp(rs.getTimestamp("Timestamp"));
              i++;
          }
          return ActivityLog;
    }

    /**
     * Method to count Rows of Database
     * @param resultSet
     * @return
     * @throws RmodelException.SqlException
     */
    public int getNumberRows(ResultSet resultSet) throws RmodelException.SqlException{
        try {
            if(resultSet.last()){
                int NoOfRows=resultSet.getRow();
                resultSet.beforeFirst();
                return NoOfRows;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION);
        }
    }

    /**
     * Method to close Database Connection
     * @throws RmodelException.SqlException
     */
    public void closeDbConnection() throws RmodelException.SqlException {
        this.mySqlQuery.Close();
        this.mySql.CloseConnection();
    }

}
