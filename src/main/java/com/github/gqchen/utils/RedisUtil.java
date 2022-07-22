package com.github.gqchen.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.net.URI;

/**
 * redis工具类
 * @author: guoqing.chen01@hand-china.com 2021-12-24 09:34
 **/

public class RedisUtil {

    private final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private Jedis jedis;
    private String url = null;
    private URI uri;

    public RedisUtil() {
    }

    public RedisUtil(String url) {
        this.url = url;
        this.jedis = getJedis();
    }

    /**
     * 创建redis连接
     * @return Jedis对象
     */
    private Jedis openConnect(){
        if (null == url) {
            url = System.getenv("REDIS_URL");
        }
        if (null == url) {
            throw new RuntimeException("URI创建失败");
        }
        jedis = new Jedis(url);
        return jedis;
    }

    /**
     * 判断Jedis对象是否为空，为空创建Jedis对象
     * @return jedis对象
     */
    public Jedis getJedis(){
        if(null == jedis){
            openConnect();
        }
        return jedis;
    }

    /**
     * 将<key,value>键值对存入redis中，默认过期时间为5分钟
     * @param key 键
     * @param value 值
     * @return 布尔值，是否成功
     */
    public boolean put(String key,String value) {
        Jedis connect = getJedis();
        try {
            connect.set(key,value);
            connect.expire(key,300);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            connect.close();
        }
    }

    /**
     * 将<key,value>键值对存入redis中，并指定默认过期时间
     * @param key 键
     * @param value 值
     * @param expire 过期时间
     * @return 布尔值，是否成功
     */
    public boolean put(String key,String value, int expire) {
        Jedis connect = getJedis();
        try {
            connect.set(key,value);
            connect.expire(key,expire);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            connect.close();
        }
    }

    /**
     * 判断此键是否在redis中存在
     * @param key 键
     * @return 布尔值，存在与否
     */
    public boolean exists(String key) {
        Jedis connect = getJedis();
        Boolean exists = connect.exists(key);
        connect.close();
        return exists;
    }

    /**
     * 根据键获取值
     * @param key 键
     * @return 对象，值对象
     */
    public Object get(String key) {
        Jedis connect = getJedis();
        if (connect.exists(key)) {
            String value = connect.get(key);
            connect.close();
            return value;
        }
        return null;
    }

    /**
     * 重置过期时间
     * @param key 键
     * @param time 过期时间
     * @return 返回此键的过期时间
     */
    public Long resetTime(String key, int time) {
        Jedis connect = getJedis();
        Long expire = connect.expire(key, time);
        connect.close();
        return expire;
    }

}
