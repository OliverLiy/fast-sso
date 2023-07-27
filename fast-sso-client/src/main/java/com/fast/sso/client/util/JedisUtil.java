package com.fast.sso.client.util;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * @author by:ly
 * @ClassName: JedisUtil
 * @Description: JedisUtil
 * @Date: 2021/7/15 10:53
 **/
public class JedisUtil {
    private static JedisPool jedisPool=null;

    static {
        //通过配置文件修改参数
        ResourceBundle rb=ResourceBundle.getBundle("application");
        String host = rb.getString("redis.host");
        int port = Integer.parseInt(rb.getString("redis.port"));
        int maxidle = Integer.parseInt(rb.getString("redis.maxidle"));
        int maxtotal= Integer.parseInt(rb.getString("redis.maxtotal"));
        String password=rb.getString("redis.password");
        //配置线程池
        JedisPoolConfig config=new JedisPoolConfig();
        //设置最大空闲等待数
        config.setMaxIdle(maxidle);
        //设置最大连接数
        config.setMaxTotal(maxtotal);
        if (StringUtils.isEmpty(password)){
            jedisPool=new JedisPool(config,host,port,1000);
        }else {
            jedisPool=new JedisPool(config,host,port,1000,password);
        }

    }
    //通过该方法获取jedis对象
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
