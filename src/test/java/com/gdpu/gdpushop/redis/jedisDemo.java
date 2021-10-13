package com.gdpu.gdpushop.redis;

import redis.clients.jedis.Jedis;

/**
 * @author:王浩东
 * @createTime: 2021/10/13
 */
public class jedisDemo {
    public static void main(String[] args) {
        //创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //测试
        String ping = jedis.ping();
        System.out.println("ping = " + ping);
    }

}
