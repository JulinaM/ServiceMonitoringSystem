package com.tektak.iloop.rm.common;

import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.ApacheConfig;
import com.tektak.iloop.util.configuration.ConfigProperty;

import java.io.File;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_CommonConfig {
    //@org.junit.Test
    public void Test_CommonConfigModule(){
        File file = new File("src/main/resources/mysqlConfig.properties");
        ConfigProperty configProperty = new ConfigProperty();
        configProperty.setConfig(file);

        try {
            com.tektak.iloop.util.configuration.Config config = new com.tektak.iloop.util.configuration.Config(new ApacheConfig(configProperty));
            System.out.println("Username::"+config.ReadString("username"));
            System.out.println("URL::"+config.ReadString("url"));
            System.out.println("Driver::"+config.ReadString("driver"));
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }
    //@Test
    public void Test_commonConfig(){
        try {
            CommonConfig commonConfig =new CommonConfig();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        System.out.println("Test method");
    }
    //@Test
    public void Test_getConfig(){
        CommonConfig commonCommonConfig = null;
        try {
            commonCommonConfig = new CommonConfig();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        com.tektak.iloop.util.configuration.Config config= commonCommonConfig.getConfig();
        System.out.println("===================================================================");
        System.out.println("URl::"+config.ReadString("url"));
        System.out.println("===================================================================");
    }
}
