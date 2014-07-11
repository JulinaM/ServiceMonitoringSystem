package com.tektak.iloop.rm.common;

import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.ApacheConfig;
import com.tektak.iloop.util.configuration.Config;
import com.tektak.iloop.util.configuration.ConfigProperty;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by tektak on 7/4/14.
 */
public class CommonConfig {
    private File file;
    private static Config config = null;
    private HttpServletRequest httpServletRequest;

    /**
     * Constructor to load static properties file
     * @throws BaseException.ConfigError
     *
     */
    public CommonConfig() throws BaseException.ConfigError{
        if(this.config==null){
            this.file=new File("src/main/resources/configuration.properties");
            ConfigProperty configProperty=new ConfigProperty();
            configProperty.setConfig(this.file);
            this.config=new Config(new ApacheConfig(configProperty));
        }
    }

    /**
     * Paramitarized constructor
     * @param filePath
     * @throws BaseException.ConfigError
     *
     */
    public CommonConfig(String filePath) throws BaseException.ConfigError{
        if(this.config==null){
            this.file=new File(filePath);
            ConfigProperty configProperty=new ConfigProperty();
            configProperty.setConfig(this.file);
            this.config=new com.tektak.iloop.util.configuration.Config(new ApacheConfig(configProperty));
        }
    }
    public CommonConfig(HttpServletRequest httpServletRequest) throws BaseException.ConfigError{
        if(this.config==null){
            this.httpServletRequest=httpServletRequest;
            String filePath = this.httpServletRequest.getSession().getServletContext().getRealPath("WEB-INF/classes/configuration.properties");
            this.file=new File(filePath);
            ConfigProperty configProperty=new ConfigProperty();
            configProperty.setConfig(this.file);
            this.config=new Config(new ApacheConfig(configProperty));
        }
    }

    /**
     * method to return CommonConfig object
     * @return
     */
    public com.tektak.iloop.util.configuration.Config getConfig(){
        if(config==null){
            return null;
        }else{
            return this.config;
        }
    }
}
