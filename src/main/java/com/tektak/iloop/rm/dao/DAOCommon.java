package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rmodel.RmodelException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by tektak on 7/8/14.
 * Common methods for DAO
 */
public class DAOCommon {
    /**
     * @param resultSet
     * @return number of rows in the resultset
     * @throws RmodelException.SqlException
     */
    public static int countRows(ResultSet resultSet) throws RmodelException.SqlException {
        try {
            if (resultSet.last()) {
                int NoOfRows = resultSet.getRow();
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
     * Method to check selected columns
     * @param rs
     * @param columnName
     * @return true or false
     */

    public static boolean IsValidColumn(ResultSet rs,String columnName){
        try {
            ResultSetMetaData rsMetaData = null;
            rsMetaData = rs.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            for (int i = 1; i < numberOfColumns+1; i++) {
                String tmp = rsMetaData.getColumnName(i);
                if(tmp.equals(columnName))
                    return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
