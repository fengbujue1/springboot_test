package com.zyj.springboot_test.test.java.inWork;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyj.springboot_test.util.DateUtil;
import com.zyj.springboot_test.util.TextFormat;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerContext;

import java.io.File;
import java.util.*;

/**
 * @Author 周赟吉
 * @Date 2022/5/23 14:31
 * @Description :
 */
public class Test {
    public static void main(String[] args) throws MultiFieldException {
//        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
        test7();

    }
    public static void test7() throws MultiFieldException {
        System.out.println(StringUtils.isNumeric(""));;
    }
    public static void test6() throws MultiFieldException {
        String records = "serial_no|acno|cur_code|tr_acdt|tr_time|tr_time|host_serial_no|tr_bankno|acname|opp_acno|opp_cur_code|opp_acname|opp_bankname|opp_bankno|tr_type|csh_dra_flag|bank_flag|area_flag|fee_amt|tr_from|trans_flag|old_serial_no|old_acdt|cash_flag|crdr_flag|amt|balance|last_bal|freeze_amt|cert_type|cert_batchno|cert_no|tr_code|user_no|sub_no|purpose|postscript|tr_timestamp|reserved1|reserved2|tr_bankname|bank_no|bankname|printcount|payamt|rcvamt|\n" +
                "|114721000000861|01|20220329|002045|002045|20220329-999800090126061484-340|0037801||114715626000520000049|01|应付智能存款息||0037801|0||||0.00|101|0|||1|C|564258.79|799209.42|0.00|0.00||||02    |99980009||入息||00000340|||北京分行营业部|313100012010|大连银行股份有限公司北京分行||0.00|564258.79|";
        MultiFieldSet set = new MultiFieldSet(records, 46);
        while (set.next()) {
            System.out.println(set.getValue("opp_bankno"));
        }
    }
    public static void test5() {
        Date date = DateUtil.addMonth(new Date(), -12);
        String yyyyMMdd = TextFormat.formatDate8(date);
        System.out.println(yyyyMMdd);

    }
    public static void test4() {

        HashMap<String, String> map = new HashMap<>();
//        map.put("1", "1");
        Integer re = (Integer) Integer.parseInt(map.get("1"));
        System.out.println(re);
    }
    public static void test3() {
        String path = "D:\\1.NSTC\\demand\\2022.6\\tmp";
        File file = new File(path);
        String[] list = file.list();
        for (String fileName : list) {
            String[] name = fileName.split("_");
            String[] split = name[3].split("\\.");
            System.out.println(split);
        }
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
