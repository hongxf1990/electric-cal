package com.zer.electric.utils;

import com.zer.electric.utils.Redis.DbConfig;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author hongxf
 * @create 2016-12-01 15:56
 */
@Component
public class Pool {
    private static JedisPool pool;

    private Pool() {
        DbConfig config = new DbConfig();
        String redisUrl = config.getRedisUrl();
        pool = new JedisPool(new JedisPoolConfig(), redisUrl);
    }

    public static JedisPool getPool() {
        return pool;
    }

    public Jedis getConn() {
        return pool.getResource();
    }

    public void closeConn(Jedis conn) {
        conn.close();
    }
}
