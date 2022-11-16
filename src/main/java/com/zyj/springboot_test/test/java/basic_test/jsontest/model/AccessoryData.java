package com.zyj.springboot_test.test.java.basic_test.jsontest.model;


import java.util.Arrays;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 * 附件数据模型
 *
 * @author 周赟吉
 * @since 2021/12/10
 */
public class AccessoryData {
    private String imgName;//文件名称，必填、非空。
    private byte[] imgData;//文件数据，必填、非空。base64转码的文件数据

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }

    @Override
    public String toString() {
        return "AccessoryData{" +
                "imgName='" + imgName + '\'' +
                ", imgData=" + Arrays.toString(imgData) +
                '}';
    }
}
