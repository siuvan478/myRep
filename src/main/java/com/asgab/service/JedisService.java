package com.asgab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisService {

    private static final String USER_REG_KEY = "user:reg:verifyCode";

    private static final String USER_RESET_PWD_KEY = "user:resetPwd:verifyCode";

    @Autowired
    private JedisPool jedisPool;

    //获取key的value值
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String str = "";
        try {
            str = jedis.get(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String str = "";
        try {
            str = jedis.set(key, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public String setex(String key, int seconds, String value) {
        Jedis jedis = jedisPool.getResource();
        String str = "";
        try {
            str = jedis.setex(key, seconds, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void delete(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hashDelete(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hdel(key, field);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hashPut(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset(key, field, value);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String hashGet(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = "";
        try {
            result = jedis.hget(key, field);
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
