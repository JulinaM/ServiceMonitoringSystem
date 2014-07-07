package com.tektak.iLoop.Rm.Common;

import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.ApacheConfig;
import com.tektak.iloop.util.configuration.Config;
import com.tektak.iloop.util.configuration.ConfigProperty;

import java.io.File;

/**
 * Created by tektak on 7/4/14.
 */
public class CommonConfig {
    private File file;
    private Config config = null;

    public CommonConfig() throws BaseException.ConfigError {
        if(this.config==null){
            this.file=new File("src/main/resources/mysqlConfig.properties");
            ConfigProperty configProperty=new ConfigProperty();
            configProperty.setConfig(this.file);
            this.config=new Config(new ApacheConfig(configProperty));
        }
    }

    public Config getConfig(){
        if(config==null){
            return null;
        }else{
            return this.config;
        }
    }


}
