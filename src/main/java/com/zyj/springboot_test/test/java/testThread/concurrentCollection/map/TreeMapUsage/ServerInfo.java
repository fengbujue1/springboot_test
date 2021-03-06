package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.TreeMapUsage;

/**
 */
public class ServerInfo {
    private String ip;// IP
    private String name;// 名称

    public ServerInfo(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 复写toString方法，使用节点IP当做hash的KEY
     */
    @Override
    public String toString() {
        return ip;
    }
}
