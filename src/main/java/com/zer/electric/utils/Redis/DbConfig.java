package com.zer.electric.utils.Redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zer
 * @create 2016-12-01 15:07
 */
public class DbConfig {

    private String redisUrl;

    public DbConfig() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            this.redisUrl = p.getProperty("redisUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getRedisUrl() {
        return redisUrl;
    }
}
