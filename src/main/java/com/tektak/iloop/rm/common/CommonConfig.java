package com.tektak.iloop.rm.common;

import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.ApacheConfig;
import com.tektak.iloop.util.configuration.Config;
import com.tektak.iloop.util.configuration.ConfigProperty;

import java.io.File;
import java.net.URL;

/**
 * Created by tektak on 7/4/14.
 */
public class CommonConfig {
    private static Config config = null;
    private File file;

    /**
     * Constructor to load static properties file
     *
     * @throws BaseException.ConfigError
     */
    public CommonConfig() throws BaseException.ConfigError {
        if (config == null) {
            this.file = new File("src/main/resources/configuration.properties");
            ConfigProperty configProperty = new ConfigProperty();
            configProperty.setConfig(this.file);
            config = new Config(new ApacheConfig(configProperty));
        }
    }

    /**
     * Paramitarized constructor
     *
     * @param filePath
     * @throws BaseException.ConfigError
     */
    public CommonConfig(String filePath) throws BaseException.ConfigError {
        if (config == null) {
            this.file = new File(filePath);
            ConfigProperty configProperty = new ConfigProperty();
            configProperty.setConfig(this.file);
            config = new Config(new ApacheConfig(configProperty));
        }
    }

    /**
     * Parameterized consturctor
     *
     * @param url
     * @throws BaseException.ConfigError
     */
    public CommonConfig(URL url) throws BaseException.ConfigError {
        if (config == null) {
            ConfigProperty configProperty = new ConfigProperty();
            configProperty.setConfig(url);
            config = new Config(new ApacheConfig(configProperty));
        }
    }

    /**
     * method to return CommonConfig object
     *
     * @return
     */
    public static Config getConfig() {
        if (config == null) {
            return null;
        } else {
            return config;
        }
    }

    public static void closeConfig(){
        if(config!=null){
           config=null;
        }
    }
}
