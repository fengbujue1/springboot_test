package com.zyj.springboot_test.test.redis.jedis;

import redis.clients.jedis.Jedis;

public class NormalUsageJedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.0.2", 6320);
        String ret_s = jedis.set("hello", "1");
        String ret_g = jedis.get("hello1");


        System.out.println(ret_s);
        System.out.println(ret_g);
    }
}
