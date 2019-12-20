package com.zyj.springboot_test.test.redis_jedis;

import com.zyj.springboot_test.test.JAVA_IO.read_file.FileUtil;
import redis.clients.jedis.Jedis;

public class LuaUsageJedis {
    public static String key1 = "key1";
    public static String key2 = "key2";

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6320);
        jedis.scriptFlush();
//        testReturn(jedis);
        testLua(jedis);

    }

    /**
     *测试功能
     * 是否有 key1 key2,有的话全删除
     * 没有的话，存入，并且再存一个 他们的和（键是和，值也是和）
     * @param jedis
     */
    public static void testLua(Jedis jedis) {
        String luaString = FileUtil.readFileFromClassPath("lua\\test.lua");
        Object eval = jedis.eval(luaString, 2, key1, key2, "10", "20");
        System.out.println(eval);
    }

    /**
     * 测试返回值
     * @param jedis
     */
    public static void testReturn(Jedis jedis) {
        String luaString = FileUtil.readFileFromClassPath("lua\\test_return.lua");
        Object eval = jedis.eval(luaString, 1, "testReturn", "hello");
        System.out.println(eval);
    }


}

