package com.tektak.iLoop.Rm.Application.logSystem;

import com.tektak.iLoop.Rm.Common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by tektak on 7/7/14.
 */
public class LogGenerator_Test {
    @Test
    public void generate_Test(){
        try {
            LogGenerator.generate(101, "10.10.10.10", "Log generator method tested successfully");
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
