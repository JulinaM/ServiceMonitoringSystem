package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.UserActivityLogDM;
import com.tektak.iloop.rm.datamodel.UserDetail;
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
public class UserActivityLogDAO {
    /**
     * Store mysql connection
     */
    private MySql mySql;
    private int RowNo;
    private int RowSize;
    private String TABLE_NAME1 = "UserActivityLog";
    private String TABLE_NAME2 = "userDetail";
    /**
     * Store MySqlQuery operation
     */
    private MySqlQuery mySqlQuery;

    /**
     * Constructor to instantiate DBConnection and getMySql object
     *
     * @throws com.tektak.iloop.rm.common.RmException.DBConnectionError
     * @throws BaseException.ConfigError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public UserActivityLogDAO() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException {
        this.mySql = new DBConnection().Connect();
        this.mySqlQuery = new MySqlQuery();
        this.mySqlQuery.setSql(this.mySql);
        this.setRowNo(0);
        this.setRowSize(100);
    }

    public int getRowNo() {
        return RowNo;
    }

    public void setRowNo(int rowNo) {
        RowNo = rowNo;
    }

    public int getRowSize() {
        return RowSize;
    }

    public void setRowSize(int rowSize) {
        RowSize = rowSize;
    }

    /**
     * Method to set Limit value for use on SELECT query
     *
     * @param rowNo
     * @param rowSize
     */
    public void setLimit(int rowNo, int rowSize) {
        this.setRowNo(rowNo);
        this.setRowSize(rowSize);
    }

    /**
     * Method to get last records logId
     * @return integer value i.e logId of last record
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int getRecentLogId() throws RmodelException.SqlException, RmodelException.CommonException {
        String query = "SELECT logId FROM `%s` order by logId desc limit 1";
        query = String.format(query, TABLE_NAME1);
        this.mySqlQuery.setQuery(query);
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
        ResultSet rs = this.mySqlQuery.Drl();
        try {
            rs.next();
            return rs.getInt("logId");
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    public UserActivityLogDM[] selectByLogId(int logId) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "SELECT logId,UId,IPaddress,Activity,Timestamp FROM %s WHERE logId=?";
            query = String.format(query, TABLE_NAME1);

            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, logId);

            ResultSet rs = this.mySqlQuery.Drl();
            return fillLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }


    /**
     * Method to fetch UserActivity log from ResultSet and return as ActivityLog array
     *
     * @param rs ResultSet type
     * @return UserActiviyLog array
     * @throws RmodelException.SqlException
     */
    public UserActivityLogDM[] fillLog(ResultSet rs) throws RmodelException.SqlException {
        int RowCount = this.getNumberRows(rs);
        UserActivityLogDM[] ActivityLog = new UserActivityLogDM[RowCount];
        int i = 0;
        UserDetail userDetail=null;
        try {
            while (rs.next()) {
                userDetail = new UserDetail();
                if(DAOCommon.IsValidColumn(rs,"userId"))
                    userDetail.setUserId(rs.getInt("userId"));

                if(DAOCommon.IsValidColumn(rs,"userName"))
                    userDetail.setUserName(rs.getString("userName"));

                if(DAOCommon.IsValidColumn(rs,"userEmail"))
                    userDetail.setUserEmail(rs.getString("userEmail"));

                if(DAOCommon.IsValidColumn(rs,"userStatus"))
                    userDetail.setUserStatus(rs.getString("userStatus"));

                if(DAOCommon.IsValidColumn(rs,"userRole"))
                    userDetail.setUserRole(rs.getString("userRole"));

                if(DAOCommon.IsValidColumn(rs,"joinDate"))
                    userDetail.setJoinDate(rs.getDate("joinDate"));



                ActivityLog[i] = new UserActivityLogDM();
                ActivityLog[i].setUserDetail(userDetail);
                if(DAOCommon.IsValidColumn(rs,"logId"))
                    ActivityLog[i].setLogId(rs.getInt("logId"));

                if(DAOCommon.IsValidColumn(rs,"UId"))
                ActivityLog[i].setUID(rs.getInt("UId"));

                if(DAOCommon.IsValidColumn(rs,"IPaddress"))
                ActivityLog[i].setIPaddress(rs.getString("IPaddress"));

                if(DAOCommon.IsValidColumn(rs,"Activity"))
                ActivityLog[i].setUserActivity(rs.getString("Activity"));

                if(DAOCommon.IsValidColumn(rs,"Timestamp"))
                ActivityLog[i].setTimestamp(rs.getTimestamp("Timestamp"));
                i++;
            }
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
        return ActivityLog;
    }

    /**
     * Method to Create UserActivityLog Table if not exist.
     *
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int CreateLogTable() throws RmodelException.SqlException, RmodelException.CommonException {
        String query = "create table if not exists %s ( logId int(11) auto_increment, UId int(11) not null,IPaddress varchar(50),Activity varchar(255),Timestamp timestamp, primary key(logId) ); ";
        query = String.format(query, TABLE_NAME1);
        this.mySqlQuery.setQuery(query);
        this.mySqlQuery.InitPreparedStatement();
        return this.mySqlQuery.Dml();
    }

    /**
     * Method to Insert log data in Mysql Database
     *
     * @param log
     * @return integer value for rows inserted
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int insert(UserActivityLogDM log) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "INSERT INTO %s (UId,IPaddress,Activity,Timestamp) VALUES(?,?,?,?)";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = mySqlQuery.getPreparedStatement();
            ps.setInt(1, log.getUID());
            ps.setString(2, log.getIPaddress());
            ps.setString(3, log.getUserActivity());
            ps.setTimestamp(4, log.getTimestamp());
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to select all logs
     *
     * @return ULogDM type object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public UserActivityLogDM[] selectAllLog() throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId LIMIT ?,?";
            query = String.format(query, TABLE_NAME1, TABLE_NAME2);

            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, this.getRowNo());
            ps.setInt(2, this.getRowSize());

            ResultSet rs = this.mySqlQuery.Drl();
            return fillLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to select logs by user Id
     *
     * @param UId
     * @return UserLog object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */

    public UserActivityLogDM[] selectLogByUser(int UId) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query = "SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId WHERE UId=? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1, TABLE_NAME2);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setInt(2, this.getRowNo());
            ps.setInt(3, this.getRowSize());
            ResultSet rs = this.mySqlQuery.Drl();
            return fillLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }

    }

    /**
     * Method to select logs on the basis of serch text and date
     * @param search
     * @param DateGreaterThan
     * @param DateLessThan
     * @return  UserLog object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public UserActivityLogDM[] filterLog(String search, String DateGreaterThan, String DateLessThan) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query = "SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId WHERE a.Activity LIKE ? AND DATE(a.timestamp) BETWEEN ? AND ? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1, TABLE_NAME2);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
            ps.setString(1, "%" + search + "%");
            ps.setString(2, DateGreaterThan);
            ps.setString(3, DateLessThan);
            ps.setInt(4, this.getRowNo());
            ps.setInt(5, this.getRowSize());
            ResultSet rs = this.mySqlQuery.Drl();

            return fillLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to select log on the basis of User Id, text, date
     * @param UId
     * @param search
     * @param DateGreaterThan
     * @param DateLessThan
     * @return  UserLog object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public UserActivityLogDM[] filterLog(String UId, String search, String DateGreaterThan, String DateLessThan) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query = "SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId WHERE a.UId= ? AND a.Activity LIKE ? AND DATE(timestamp) BETWEEN ? AND ? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1, TABLE_NAME2);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();

            ps.setString(1, UId);
            ps.setString(2, "%" + search + "%");
            ps.setString(3, DateGreaterThan);
            ps.setString(4, DateLessThan);
            ps.setInt(5, this.getRowNo());
            ps.setInt(6, this.getRowSize());

            ResultSet rs = this.mySqlQuery.Drl();
            return fillLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to delete log by unique log Id
     * @param logId
     * @return integer value to indicate no. of logs deleted
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int deleteLogById(int logId) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="DELETE FROM %s WHERE logId=? ";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, logId);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
    }

    /**
     * Method to delete all logs
     * @return integer value to indicate no. of logs deleted
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int deleteAllLog() throws RmodelException.SqlException, RmodelException.CommonException {

        String query="DELETE FROM %s";
        query = String.format(query, TABLE_NAME1);
        this.mySqlQuery.setQuery(query);
        this.mySqlQuery.InitPreparedStatement();
        return this.mySqlQuery.Dml();
    }

    /**
     * Method to delete log by User Id
     * @param UId
     * @return integer value to indicate no. of logs deleted
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int deleteLogByUser(int UId) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="DELETE FROM %s WHERE UId= ?";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps = this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION, e);
        }
        return this.mySqlQuery.Dml();
    }
    public int getNumberRows(ResultSet resultSet) throws RmodelException.SqlException {
        return DAOCommon.countRows(resultSet);
    }

    /**
     * Method to close Database Connection
     *
     * @throws RmodelException.SqlException
     */
    public void closeDbConnection() {
        try {
            this.mySqlQuery.Close();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
    }
}
