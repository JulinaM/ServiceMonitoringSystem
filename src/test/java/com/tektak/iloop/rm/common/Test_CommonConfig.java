package com.tektak.iloop.rm.common;

import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.ApacheConfig;
import com.tektak.iloop.util.configuration.Config;
import com.tektak.iloop.util.configuration.ConfigProperty;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by tektak on 7/7/14.
 */
public class Test_CommonConfig {
    @org.junit.Test
    public void Test_CommonConfigModule(){
        File file = new File("src/main/resources/configuration.properties");
        ConfigProperty configProperty = new ConfigProperty();
        configProperty.setConfig(file);
        Config config=null;

        try {
            config = new Config(new ApacheConfig(configProperty));
            System.out.println("Username::"+config.ReadString("username"));
            System.out.println("URL::"+config.ReadString("url"));
            System.out.println("Driver::"+config.ReadString("driver"));
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        Assert.assertEquals("root",config.ReadString("username"));
        Assert.assertEquals("jdbc:mysql://10.0.3.107:3306/",config.ReadString("url"));
        Assert.assertEquals("com.mysql.jdbc.Driver",config.ReadString("driver"));
        Assert.assertEquals("rmmodel",config.ReadString("dbName"));
        Assert.assertEquals("tektak",config.ReadString("password"));
    }
    @Test
    public void Test_commonConfig(){
        try {
            CommonConfig commonConfig =new CommonConfig("src/main/resources/configuration.properties");
            Assert.assertNotNull(commonConfig);
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        System.out.println("Test method");

    }
    @Test
    public void Test_getConfig(){
        CommonConfig commonCommonConfig = null;
        Config config=null;
        try {
            commonCommonConfig = new CommonConfig("src/main/resources/configuration.properties");
            config= commonCommonConfig.getConfig();
            Assert.assertEquals("root",config.ReadString("username"));
            Assert.assertEquals("jdbc:mysql://10.0.3.107:3306/",config.ReadString("url"));
            Assert.assertEquals("com.mysql.jdbc.Driver",config.ReadString("driver"));
            Assert.assertEquals("rmmodel",config.ReadString("dbName"));
            Assert.assertEquals("tektak",config.ReadString("password"));
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }

        System.out.println("===================================================================");
        System.out.println("URl::"+config.ReadString("url"));
        System.out.println("===================================================================");
    }
}
