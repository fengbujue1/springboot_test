package com.zyj.springboot_test.test.java.inWork;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zyj.springboot_test.controller.Hello;
import com.zyj.springboot_test.test.java.arithmetic.Base64Util;
import com.zyj.springboot_test.test.java.zip.ZipUtil;
import com.zyj.springboot_test.util.DateUtil;
import com.zyj.springboot_test.util.PDFUtil;
import com.zyj.springboot_test.util.TextFormat;
import com.zyj.springboot_test.util.XMLUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Element;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Author 周赟吉
 * @Date 2022/5/23 14:31
 * @Description :
 */
public class Test2 {


    public static void main(String[] args) throws Exception {
        int step = 30;
        int size = 100;
        for (int i = 0; i < size; i++) {
            if (i % step == 0 && i > 0) {
                System.out.println("插入新的待签收票据信息:第" + (i - step+1) + "到第" + i + "条数据");
            }
        }
        if (size % step == 0) {
            System.out.println("插入新的待签收票据信息:第" + ((size / step - 1) * step + 1) + "到第" + size + "条数据");
        } else {
            System.out.println("插入新的待签收票据信息:第" + (size / step * step + 1) + "到第" + size + "条数据");
        }
    }

    public static class OBJ {
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "OBJ{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }

}
