package com.zyj.springboot_test.test.redis_jedis;

import com.zyj.springboot_test.util.FileUtil;
import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class LuaUsageJedis {
    public static String key1 = "key1";
    public static String key2 = "key2";

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6320);
//        testReturn(jedis);
//        testLua(jedis);
//        testEvalsha(jedis);


        //用固定的校验和测试特定脚本是否存在
//        jedis.scriptFlush();
//        Boolean exists = jedis.scriptExists("fb5e50cbbbda997fd6b62a6be20560fffcc47632");
//        System.out.println(exists);
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
     * 测试脚本缓存相关
     * @param jedis
     */
    public static void testEvalsha(Jedis jedis) {
        String luaString = FileUtil.readFileFromClassPath("lua\\test_evalsha.lua");
        Scanner scanner = new Scanner(System.in);
        String sha1 = jedis.scriptLoad(luaString);//将脚本存入redis
        System.out.println("校验和:"+sha1);
        while (true) {
            System.out.println("请输入键：值");
            String s = scanner.nextLine();
            String[] strings = s.split(":");
            if ("quit".equals(strings[0])) {
                break;
            }


            //检验脚本是否存在
            Boolean exists = jedis.scriptExists(sha1);
            if (exists) {
                //执行evalsha 用sha1校验和 执行脚本
                Object evalsha = jedis.evalsha(sha1,1, strings[0], strings[1]);
                System.out.println(evalsha);
            }


        }


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

