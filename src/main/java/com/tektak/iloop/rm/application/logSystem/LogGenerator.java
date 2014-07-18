package com.tektak.iloop.rm.application.logSystem;

import com.tektak.iloop.rm.common.DateTime;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.dao.UserActivityLogDAO;
import com.tektak.iloop.rm.datamodel.UserActivityLogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;

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
     * @throws com.tektak.iloop.rm.common.RmException.DBConnectionError
     * @throws RmodelException.SqlException
     * @throws RmodelException.CommonException
     * @throws BaseException.ConfigError
     *
     */
    public static int generateLog(int UId, String IPaddress, String UserActivity) throws RmException.DBConnectionError, RmodelException.SqlException, RmodelException.CommonException, BaseException.ConfigError{
        UserActivityLogDM log=new UserActivityLogDM();
        log.setUID(UId);
        log.setIPaddress(IPaddress);
        log.setUserActivity(UserActivity);
        log.setTimestamp(DateTime.getTimestamp());

        UserActivityLogDAO DAO=new UserActivityLogDAO();

        int AffectedRows = DAO.insert(log);
        DAO.closeDbConnection();
        return AffectedRows;
    }
}
