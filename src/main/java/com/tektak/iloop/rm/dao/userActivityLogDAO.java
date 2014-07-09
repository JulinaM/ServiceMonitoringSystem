package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserActivityLogDM;
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
     * @throws com.tektak.iloop.rm.common.RmException.DBConnectionError
     * @throws BaseException.ConfigError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public userActivityLogDAO() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException{
         this.mySql=new DBConnection().Connect();
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
     *
     */
    public int WriteLog(UserActivityLogDM log) throws RmodelException.SqlException, RmodelException.CommonException{
        if(mySqlQuery!=null&&mySql!=null) {

        }
            try {
                this.mySqlQuery.setQuery("INSERT INTO UserActivityLog (UId,IPaddress,Activity,Timestamp) VALUES(?,?,?,?)");
                this.mySqlQuery.InitPreparedStatement();
                PreparedStatement ps = mySqlQuery.getPreparedStatement();
                ps.setInt(1, log.getUID());
                ps.setString(2, log.getIPaddress());
                ps.setString(3, log.getUserActivity());
                ps.setTimestamp(4, log.getTimestamp());
            } catch (SQLException e) {
                throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
            }


            return this.mySqlQuery.Dml();


    }

    /**
     * Method to Read User Activity Log information
     * @return  UserActivityLogDM type object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */
    public UserActivityLogDM[] ReadAllLog() throws RmodelException.SqlException, RmodelException.CommonException{
          this.mySqlQuery.setQuery("SELECT * FROM UserActivityLog");
          this.mySqlQuery.InitPreparedStatement();
          ResultSet rs=this.mySqlQuery.Drl();
          int RowCount=this.getNumberRows(rs);
          UserActivityLogDM[] ActivityLog=new UserActivityLogDM[RowCount];
          int i=0;
        try {
            while(rs.next()) {
                ActivityLog[i]=new UserActivityLogDM();
                ActivityLog[i].setUID(rs.getInt("UId"));
                ActivityLog[i].setIPaddress(rs.getString("IPaddress"));
                ActivityLog[i].setUserActivity(rs.getString("Activity"));
                ActivityLog[i].setTimestamp(rs.getTimestamp("Timestamp"));
                i++;
            }
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
        return ActivityLog;
    }

    /**
     * Method to read ActivityLog by UId
     * @param UId
     * @return
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */

    public UserActivityLogDM[] ReadLogByUser(String UId) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("SELECT * FROM UserActivityLog WHERE UId=?");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setString(1, UId);
            ResultSet rs=this.mySqlQuery.Drl();
            int RowCount=this.getNumberRows(rs);
            UserActivityLogDM[] ActivityLog=new UserActivityLogDM[RowCount];
            int i=0;
            while(rs.next()) {
                ActivityLog[i]=new UserActivityLogDM();
                ActivityLog[i].setUID(rs.getInt("UId"));
                ActivityLog[i].setIPaddress(rs.getString("IPaddress"));
                ActivityLog[i].setUserActivity(rs.getString("Activity"));
                ActivityLog[i].setTimestamp(rs.getTimestamp("Timestamp"));
                i++;
            }
            return ActivityLog;
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }

    }

    /**
     * method to filter log by datetime
     * @param DateTime
     * @return
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */

    public UserActivityLogDM[] ReadLogByDateTime(String DateTime) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("SELECT * FROM UserActivityLog WHERE Timestamp LIKE ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        try {
            ps.setString(1, "%"+DateTime+"%");
            ResultSet rs=this.mySqlQuery.Drl();
            int RowCount=this.getNumberRows(rs);
            UserActivityLogDM[] ActivityLog=new UserActivityLogDM[RowCount];
            int i=0;
            while(rs.next()) {
                ActivityLog[i] = new UserActivityLogDM();
                ActivityLog[i].setUID(rs.getInt("UId"));
                ActivityLog[i].setIPaddress(rs.getString("IPaddress"));
                ActivityLog[i].setUserActivity(rs.getString("Activity"));
                ActivityLog[i].setTimestamp(rs.getTimestamp("Timestamp"));
                i++;
            }
            return ActivityLog;
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }

    }

    /**
     * Filter UserActivityLog by User and DateTime
     * @param UId
     * @param DateTime
     * @return
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */
    public UserActivityLogDM[] ReadLogByUserNDateTime(String UId, String DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("SELECT * FROM UserActivityLog WHERE UId LIKE ? AND Timestamp LIKE ?");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setString(1, "%"+UId+"%");
            ps.setString(2, "%"+DateTime+"%");
            ResultSet rs=this.mySqlQuery.Drl();
            int RowCount=this.getNumberRows(rs);
            UserActivityLogDM[] ActivityLog=new UserActivityLogDM[RowCount];
            int i=0;
            while(rs.next()) {
                ActivityLog[i]=new UserActivityLogDM();
                ActivityLog[i].setUID(rs.getInt("UId"));
                ActivityLog[i].setIPaddress(rs.getString("IPaddress"));
                ActivityLog[i].setUserActivity(rs.getString("Activity"));
                ActivityLog[i].setTimestamp(rs.getTimestamp("Timestamp"));
                i++;
            }
            return ActivityLog;
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteAllLog() throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog");
        this.mySqlQuery.InitPreparedStatement();
        return this.mySqlQuery.Dml();
    }

    public int deleteLogByUser(String UId) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE UId= ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        try {
            ps.setString(1, UId);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
        return this.mySqlQuery.Dml();
    }

    public int deleteLogByDateTime(String DateTime) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE Timestamp= ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        try {
            ps.setString(1, "%"+DateTime+"%");
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteLogByUserNDateTime(String UId, String DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE UId LIKE ? AND Timestamp LIKE ?");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setString(1, "%"+UId+"%");
            ps.setString(2, "%"+DateTime+"%");
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    /**
     * Method to count Rows of ResultSet
     * @param resultSet
     * @return
     * @throws RmodelException.SqlException
     */
    public int getNumberRows(ResultSet resultSet) throws RmodelException.SqlException{
        return CommonFunction.countRows(resultSet);
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
