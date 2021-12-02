package com.zyj.springboot_test.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/2
 */
public class PropertiesUtil {
    public static Properties loadPropertiesFromFile(String fileName) {
        Properties prop = new Properties();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader =
                    new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "utf-8");
            prop.load(inputStreamReader);
        } catch (Throwable e) {

            System.out.println("无法加载属性文件。(filename=" + fileName + ";error=" + e.getMessage() + ")");
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    System.out.println("关闭流异常" + e);
                }
            }
        }
        //TODO  看这里
        System.getProperties().putAll(prop);
        return prop;
    }
}
