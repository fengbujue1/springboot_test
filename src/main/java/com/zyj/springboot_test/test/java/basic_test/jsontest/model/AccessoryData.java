package com.zyj.springboot_test.test.java.basic_test.jsontest.model;


import java.util.Arrays;

/**
 * Description:
 * �ο��ĵ��鵵�ڣ�http://192.168.0.2/svn/doc/Doc.Dev/BP/��������/Wiki/���ױ��Ĺ淶/TX0022-�羳�����֧����CIPS��.xlsx
 * ��������ģ��
 *
 * @author ���S��
 * @since 2021/12/10
 */
public class AccessoryData {
    private String imgName;//�ļ����ƣ�����ǿա�
    private byte[] imgData;//�ļ����ݣ�����ǿա�base64ת����ļ�����

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
