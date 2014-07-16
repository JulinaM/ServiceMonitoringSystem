package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rmodel.RmodelException;

import java.sql.ResultSet;
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
}
