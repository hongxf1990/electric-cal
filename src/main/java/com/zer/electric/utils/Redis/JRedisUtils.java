package com.zer.electric.utils.Redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author hongxf
 * @create 2016-12-01 15:06
 */
public class JRedisUtils {

    private static JedisPool pool;

    public static JedisPool getPool() {
        DbConfig config = new DbConfig();
        String redisUrl = config.getRedisUrl();
        pool = new JedisPool(new JedisPoolConfig(), redisUrl);
        return pool;
    }

}
