package com.zyj.springboot_test.test.java.basic_test.jsontest.model;

import java.util.List;

/**
 * Description:
 *  附件模型
 *
 * @author 周赟吉
 * @since 2021/12/10
 */
public class AccessoryModel {
    private String mesgId;//与报文中报文标识号保持一致
    private String receiver;//接收方行号
    private String reDup;//1-自动去重。其余均不去重，发现重复返回异常
    private List<AccessoryData> fileInfo;//文件信息集合

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
