package com.tektak.iLoop.Rm.Common;

import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.ApacheConfig;
import com.tektak.iloop.util.configuration.Config;
import com.tektak.iloop.util.configuration.ConfigProperty;
import org.junit.Test;

import java.io.File;

/**
 * Created by tektak on 7/7/14.
 */
public class CommonConfig_Test {
    //@org.junit.Test
    public void CommonConfigModule_Test(){
        File file = new File("src/main/resources/mysqlConfig.properties");
        ConfigProperty configProperty = new ConfigProperty();
        configProperty.setConfig(file);

        try {
            Config config = new Config(new ApacheConfig(configProperty));
            System.out.println("Username::"+config.ReadString("username"));
            System.out.println("URL::"+config.ReadString("url"));
            System.out.println("Driver::"+config.ReadString("driver"));
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }
    //@Test
    public void commonConfig_Test(){
        try {
            CommonConfig commonConfig=new CommonConfig();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        System.out.println("Test method");
    }
    //@Test
    public void getConfig_Test(){
        CommonConfig commonConfig= null;
        try {
            commonConfig = new CommonConfig();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        Config config=commonConfig.getConfig();
        System.out.println("===================================================================");
        System.out.println("URl::"+config.ReadString("url"));
        System.out.println("===================================================================");
    }
}
