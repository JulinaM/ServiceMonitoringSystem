package com.tektak.iloop.rm.dao;

import com.tektak.iloop.rm.common.DBConnection;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.rmodel.driver.MySql;
import com.tektak.iloop.rmodel.query.MySqlQuery;
import com.tektak.iloop.util.common.BaseException;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/8/14.
 */
public class Test_CDAO {
    @Test
    public void Test_countRows(){
        ULogDAO ULogDAO=null;
        try {
            ULogDAO =new ULogDAO();
            ULogDAO.deleteAllLog();

            ULogDM[] Activitylog=new ULogDM[5];
            for(int i=0;i<5;i++){
                Activitylog[i]=new ULogDM();
                Activitylog[i].setUID(i);
                Activitylog[i].setIPaddress("174.4.4."+i);
                Activitylog[i].setUserActivity("ReadAllLog() method is testing..."+i);
                Activitylog[i].setTimestamp(Timestamp.valueOf("2007-09-23 10:10:10.0"));
                ULogDAO.WriteLog(Activitylog[i]);
            }

            MySql mySql=new DBConnection().Connect();
            MySqlQuery mySqlQuery=new MySqlQuery();
            mySqlQuery.setSql(mySql);
            mySqlQuery.setQuery("SELECT * FROM UserActivityLog");
            mySqlQuery.InitPreparedStatement();
            ResultSet rs=mySqlQuery.Drl();

            int RowCount= DAOCommon.countRows(rs);

            Assert.assertEquals(RowCount,5);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }finally {
            try {
                ULogDAO.closeDbConnection();
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            }
        }

    }
}
