package com.zyj.springboot_test.test.java.basic_test.jsontest.model;

import java.util.List;

/**
 * Description:
 *  ����ģ��
 *
 * @author ���S��
 * @since 2021/12/10
 */
public class AccessoryModel {
    private String mesgId;//�뱨���б��ı�ʶ�ű���һ��
    private String receiver;//���շ��к�
    private String reDup;//1-�Զ�ȥ�ء��������ȥ�أ������ظ������쳣
    private List<AccessoryData> fileInfo;//�ļ���Ϣ����

    public String getMesgId() {
        return mesgId;
    }

    public void setMesgId(String mesgId) {
        this.mesgId = mesgId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReDup() {
        return reDup;
    }

    public void setReDup(String reDup) {
        this.reDup = reDup;
    }

    public List<AccessoryData> getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(List<AccessoryData> fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public String toString() {
        return "AccessoryModel{" +
                "mesgId='" + mesgId + '\'' +
                ", receiver='" + receiver + '\'' +
                ", reDup='" + reDup + '\'' +
                ", fileInfo=" + fileInfo +
                '}';
    }
}
