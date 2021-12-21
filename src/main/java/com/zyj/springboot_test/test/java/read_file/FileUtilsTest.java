package com.zyj.springboot_test.test.java.read_file;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/21
 */
public class FileUtilsTest {
    public static void main(String[] args) throws IOException {
        String fileName = "E:\\AaccessoryFile\\123.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            FileUtils.forceMkdirParent(file);
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map infoMap = new HashMap();
        infoMap.put("status", true);
        infoMap.put("time", df.format(new Date()));
        //多次写文件后面的写操作会覆盖前面的写操作
        FileUtils.writeByteArrayToFile(new File(fileName), JSON.toJSONString(infoMap).getBytes("UTF-8"));

    }
}
