package com.zyj.springboot_test.test.java.basic_test.jsontest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.core.util.Base64;
import com.zyj.springboot_test.test.java.basic_test.jsontest.model.AccessoryData;
import com.zyj.springboot_test.test.java.basic_test.jsontest.model.AccessoryModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.util.*;

/**
 * Description:
 *
 * @author 周赟吉
 * @since 2021/12/30
 */
public class JSONTest {
    public static void main(String[] args) {
        AccessoryModel accessoryModel = new AccessoryModel();
        accessoryModel.setMesgId("123");
        //TODO 根据理解，此处这个接收方应该指的银行，所以填写银行的CIPS ID
        accessoryModel.setReceiver("123");
        //自动去重
        accessoryModel.setReDup("-1");
        ArrayList<AccessoryData> accessoryDatas = new ArrayList<AccessoryData>();
        accessoryModel.setFileInfo(accessoryDatas);
//        for (int i = 0; i < 2; i++) {
//            AccessoryData accessoryData = new AccessoryData();
//            accessoryData.setImgName("123");
//            accessoryData.setImgData(new byte[]{1,2,3,4,5,});
//            accessoryDatas.add(accessoryData);
//        }
        accessoryDatas.add(create());
        byte[] bytes = JSON.toJSONBytes(accessoryModel);

        System.out.println(new String(bytes));
//        AccessoryModel accessoryModel1 = JSON.parseObject(new String(bytes), AccessoryModel.class);
//
//        System.out.println(accessoryModel1);

        //尝试不定义业务模型，直接获取json数据
        String s = new String(bytes);
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray fileInfo = (JSONArray) jsonObject.get("fileInfo");
        System.out.println(fileInfo);
        Iterator<Object> iterator = fileInfo.stream().iterator();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject)iterator.next();
            System.out.println(next.get("imgName"));;
        }
        System.out.println("解析完成");

    }
    private static AccessoryData create() {
        String filePath = "C:\\Users\\DELL\\Desktop\\cips编译配置文件.txt";
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        byte[] tmp = new byte[1024];
        int s;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            while ((s = fileInputStream.read(tmp)) >0) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] encode = Base64.encode(tmp);
        AccessoryData accessoryData = new AccessoryData();
        accessoryData.setImgName("cips编译配置文件.txt");
        accessoryData.setImgData(encode);
        return accessoryData;
    }
}
