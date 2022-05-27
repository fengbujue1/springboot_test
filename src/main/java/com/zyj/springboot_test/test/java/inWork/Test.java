package com.zyj.springboot_test.test.java.inWork;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.quartz.SchedulerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 周赟吉
 * @Date 2022/5/23 14:31
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
//        test1();
        test2();
    }
    public static void test1() {
        String puspose = "代理收款, 代理结算: 代理测试 666622224450 收款";
        int i = puspose.indexOf("代理结算: ") + "代理结算: ".length();
        String nameAndNum = puspose.substring(i);
        String[] split = nameAndNum.split(" ");
        String name = split[0];
        String num = split[1];
        System.out.println("name:" + name);
        System.out.println("num:" + num);
    }
    public static void test2() {
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashMap<String, String> map3 = new HashMap<>();
        map1.put("123", "123");
        map2.put("222", "222");
        map3.put("333", "333");
        ArrayList<Map> maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        String s = JSON.toJSONString(maps);
        System.out.println("jsonStr:" + s);

        List o = (List<Map<String, String>>) JSON.parse(s);
        System.out.println(o);
    }
}
