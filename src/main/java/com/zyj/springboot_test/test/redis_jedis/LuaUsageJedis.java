package com.zyj.springboot_test.test.redis_jedis;

import com.zyj.springboot_test.test.JAVA_IO.read_file.FileUtil;
import redis.clients.jedis.Jedis;

public class LuaUsageJedis {
    public static String key1 = "key1";
    public static String key2 = "key2";

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6320);
        jedis.scriptFlush();

        String luaString = FileUtil.readFileFromClassPath("lua\\test.lua");
        jedis.eval(luaString, 2, key1, key2, "10", "20");
    }


}

