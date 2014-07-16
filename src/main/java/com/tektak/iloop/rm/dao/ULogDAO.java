package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.DateTime;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/4/14.
 */
public class ULogDAO {
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
     * @throws com.tektak.iloop.rm.common.RmException.DBConnectionError
     * @throws BaseException.ConfigError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public ULogDAO() throws RmException.DBConnectionError, BaseException.ConfigError, RmodelException.SqlException, RmodelException.CommonException{
         this.mySql=new DBConnection().Connect();
         this.mySqlQuery=new MySqlQuery();
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
     * @param rowNo
     * @param rowSize
     */
    public void setLimit(int rowNo,int rowSize){
       this.setRowNo(rowNo);
        this.setRowSize(rowSize);
    }

    public int getRecentLogId() throws RmodelException.SqlException, RmodelException.CommonException{
        String query="SELECT logId FROM `%s` order by logId desc limit 1";
        query = String.format(query, TABLE_NAME1);
        this.mySqlQuery.setQuery(query);
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        ResultSet rs=this.mySqlQuery.Drl();
        try {
            rs.next();
            return rs.getInt("logId");
        } catch (SQLException e) {
           throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteLogByLogId(int logId) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE logId=? ");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1,logId);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    /**
     * Method to fetch UserActivity log from ResultSet and return as ActivityLog array
     * @param rs ResultSet type
     * @return UserActiviyLog array
     * @throws RmodelException.SqlException
     */
    public ULogDM[] fetchLog(ResultSet rs) throws RmodelException.SqlException {
        int RowCount=this.getNumberRows(rs);
        ULogDM[] ActivityLog=new ULogDM[RowCount];
        int i=0;
        try {
            while(rs.next()) {
                ActivityLog[i]=new ULogDM();
                ActivityLog[i].setLogId(rs.getInt("logId"));
                ActivityLog[i].setUID(rs.getInt("UId"));
                ActivityLog[i].setUserName(rs.getString("userName"));
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
     * Method to Create UserActivityLog Table if not exist.
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     */
    public int CreateLogTable() throws RmodelException.SqlException, RmodelException.CommonException {
        String query="create table if not exists %s ( logId int(11) auto_increment, UId int(11) not null,IPaddress varchar(50),Activity varchar(255),Timestamp timestamp, primary key(logId) ); ";
        query = String.format(query, TABLE_NAME1);
        this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            return this.mySqlQuery.Dml();
    }

    /**
     * Method to Insert log data in Mysql Database
     * @param log
     * @return  integer value for rows inserted
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */
    public int WriteLog(ULogDM log) throws RmodelException.SqlException, RmodelException.CommonException{
            try {
                String query="INSERT INTO %s (UId,IPaddress,Activity,Timestamp) VALUES(?,?,?,?)";
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
                throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
            }
    }

    /**
     * Method to Read User Activity Log information
     * @return  ULogDM type object
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */
    public ULogDM[] ReadAllLog() throws RmodelException.SqlException, RmodelException.CommonException{
        try {
            String query="SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId LIMIT ?,?";
            query = String.format(query, TABLE_NAME1,TABLE_NAME2);

            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1,this.getRowNo());
            ps.setInt(2,this.getRowSize());

            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    /**
     * Method to read ActivityLog by UId
     * @param UId
     * @return
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     *
     */

    public ULogDM[] ReadLogByUser(int UId) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId WHERE UId=? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1,TABLE_NAME2);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setInt(2, this.getRowNo());
            ps.setInt(3, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
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

    public ULogDM[] ReadLogByDateTimeGreaterThan(Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="SELECT * FROM %s WHERE Timestamp >=? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setTimestamp(1, DateTime);
            ps.setInt(2, this.getRowNo());
            ps.setInt(3, this.getRowSize());

            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }

    }

    public ULogDM[] ReadLogByDateTimeLessThan(Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="SELECT * FROM %s WHERE DATE(Timestamp) <=? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setTimestamp(1, DateTime);
            ps.setInt(2, this.getRowNo());
            ps.setInt(3, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public ULogDM[] ReadLogByDateTimeBetween(Timestamp DateTimeGreaterThan,Timestamp DateTimeLessThan) throws RmodelException.SqlException, RmodelException.CommonException {

        try {

            String query="SELECT * FROM %s WHERE DATE(Timestamp) BETWEEN ? AND ? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setTimestamp(1, DateTimeGreaterThan);
            ps.setTimestamp(2, DateTimeLessThan);
            ps.setInt(3, this.getRowNo());
            ps.setInt(4, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
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
    public ULogDM[] ReadLogByUserNDateTimeGreaterThan(int UId, Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="SELECT * FROM %s WHERE UId LIKE ? AND Timestamp >=? LIMIT ?,?";
            query = String.format(query, TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setTimestamp(2, DateTime);
            ps.setInt(3, this.getRowNo());
            ps.setInt(4, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public ULogDM[] ReadLogByUserNDateTimeLessThan(int UId, Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="SELECT * FROM %s WHERE UId LIKE ? AND Timestamp <= ? LIMIT ?,?";
            query=String.format(query,TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setTimestamp(2, DateTime);
            ps.setInt(3, this.getRowNo());
            ps.setInt(4, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public ULogDM[] ReadLogByUserNDateTimeBetween(int UId, Timestamp DateTimeLessThan,Timestamp DateTimeGreaterThan) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            String query="SELECT * FROM %s WHERE UId LIKE ? AND Timestamp <= ? AND Timestamp >= ? LIMIT ?,?";
            query=String.format(query,TABLE_NAME1);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setTimestamp(2, DateTimeLessThan);
            ps.setTimestamp(3, DateTimeGreaterThan);
            ps.setInt(4, this.getRowNo());
            ps.setInt(5, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }
    public ULogDM[] ReadLogByFilter(String DateGreaterThan,String DateLessThan) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query="SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId WHERE DATE(a.timestamp) BETWEEN ? AND ? LIMIT ?,?";
            query=String.format(query,TABLE_NAME1,TABLE_NAME2);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setString(1, DateGreaterThan);
            ps.setString(2, DateLessThan);
            ps.setInt(3, this.getRowNo());
            ps.setInt(4, this.getRowSize());
            ResultSet rs=this.mySqlQuery.Drl();
            System.out.println("Query::"+ps.toString());
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }
    public ULogDM[] ReadLogByFilter(String UId,String DateGreaterThan,String DateLessThan) throws RmodelException.SqlException, RmodelException.CommonException {
        try {
            String query="SELECT logId,UId,userName,IPaddress,Activity,Timestamp FROM %s as a INNER JOIN %s as b on b.userId = a.UId WHERE a.UId= ? AND DATE(timestamp) BETWEEN ? AND ? LIMIT ?,?";
            query=String.format(query,TABLE_NAME1,TABLE_NAME2);
            this.mySqlQuery.setQuery(query);
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setString(1, UId);
            ps.setString(2, DateGreaterThan);
            ps.setString(3, DateLessThan);
            ps.setInt(4, this.getRowNo());
            ps.setInt(5, this.getRowSize());
            System.out.println("----Query:"+ps.toString());

            ResultSet rs=this.mySqlQuery.Drl();
            return fetchLog(rs);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }
    public int deleteAllLog() throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog");
        this.mySqlQuery.InitPreparedStatement();
        return this.mySqlQuery.Dml();
    }

    public int deleteLogByUser(int UId) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE UId= ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        try {
            ps.setInt(1, UId);
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
        return this.mySqlQuery.Dml();
    }

    public int deleteLogByDateTimeGreaterThan(Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE Timestamp >= ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        try {
            ps.setTimestamp(1, DateTime);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteLogByDateTimeLessThan(Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE Timestamp <= ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
        try {
            ps.setTimestamp(1, DateTime);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteLogByDateTimeBetween(Timestamp DateTimeAbove,Timestamp DateTimeBelow) throws RmodelException.SqlException, RmodelException.CommonException {
        this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE Timestamp >= ? AND Timestamp < ?");
        this.mySqlQuery.InitPreparedStatement();
        PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
         try {
            ps.setTimestamp(1, DateTimeAbove);
            ps.setTimestamp(2, DateTimeBelow);
            System.out.println("prepared Statement ::"+ps.toString()+DateTimeAbove+" <=Timestamp "+DateTimeBelow);

             return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }



    public int deleteLogByUserNDateTimeGreaterThan(int UId, Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE UId = ? AND Timestamp >= ?");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setTimestamp(2, DateTime);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteLogByUserNDateTimeLessThan(int UId, Timestamp DateTime) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE UId = ? AND Timestamp <= ?");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setTimestamp(2, DateTime);
            return this.mySqlQuery.Dml();
        } catch (SQLException e) {
            throw new RmodelException.SqlException(RmodelException.SQL_EXCEPTION,e);
        }
    }

    public int deleteLogByUserNDateTimeBetween(int UId, Timestamp DateTimeLessThan, Timestamp DateTimeGreaterThan) throws RmodelException.SqlException, RmodelException.CommonException {

        try {
            this.mySqlQuery.setQuery("DELETE FROM UserActivityLog WHERE UId = ? AND Timestamp <= ? AND Timestamp >= ?");
            this.mySqlQuery.InitPreparedStatement();
            PreparedStatement ps=this.mySqlQuery.getPreparedStatement();
            ps.setInt(1, UId);
            ps.setTimestamp(2, DateTimeLessThan);
            ps.setTimestamp(2, DateTimeGreaterThan);
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
        return DAOCommon.countRows(resultSet);
    }

    /**
     * Method to close Database Connection
     * @throws RmodelException.SqlException
     */
    public void closeDbConnection(){
        try {
            this.mySqlQuery.Close();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        }
    }
}
