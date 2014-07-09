package com.tektak.iloop.rm.application.logSystem;

import com.tektak.iLoop.rm.application.logSystem.LogGenerator;
import com.tektak.iLoop.rm.common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by tektak on 7//14.
 */
public class Test_LogGenerator {
    //@Test
    public void Test_generate(){
        try {
            LogGenerator.generateLog(101, "10.10.10.111", "Log generator method tested successfully");
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
