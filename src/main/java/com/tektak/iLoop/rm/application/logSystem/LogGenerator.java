package com.tektak.iLoop.rm.application.logSystem;

import com.tektak.iLoop.rm.common.DateTime;
import com.tektak.iLoop.rm.common.RmException;
import com.tektak.iLoop.rm.dao.userActivityLogDAO;
import com.tektak.iLoop.rm.datamodel.UserActivityLogDataModel;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;

import java.sql.SQLException;

/**
 * Created by tektak on 7/3/14.
 */

/**
 * Class for generating UserActivity log
 */
public class LogGenerator {
    /**
     * Method to generate log and write in database
     * @param UId
     * @param IPaddress
     * @param UserActivity
     * @throws RmException.DBConnectionError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     * @throws BaseException.ConfigError
     * @throws SQLException
     */
    public static void generateLog(int UId, String IPaddress, String UserActivity) throws RmException.DBConnectionError, RmodelException.SqlException, RmodelException.CommonException, BaseException.ConfigError, SQLException{
        UserActivityLogDataModel log=new UserActivityLogDataModel();
        log.setUID(UId);
        log.setIPaddress(IPaddress);
        log.setUserActivity(UserActivity);
        log.setTimestamp(DateTime.getTimestamp());

        userActivityLogDAO DAO=new userActivityLogDAO();
        DAO.WriteLog(log);
        DAO.closeDbConnection();
    }
}
