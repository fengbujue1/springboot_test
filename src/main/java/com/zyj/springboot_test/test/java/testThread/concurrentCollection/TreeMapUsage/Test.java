package com.zyj.springboot_test.test.java.testThread.concurrentCollection.TreeMapUsage;

import java.util.HashSet;

/**
 * Mecached、MyCat 负载均衡、一致性hash算法实现演练
 */
public class Test {
    public static void main(String[] args) {
        HashSet<ServerInfo> set = new HashSet<ServerInfo>();
        // 准备4台机器作为服务集群
        set.add(new ServerInfo("192.168.1.1","9001"));
        set.add(new ServerInfo("192.168.1.2","9002"));
        set.add(new ServerInfo("192.168.1.3","9003"));
        set.add(new ServerInfo("192.168.1.4","9004"));
        
        // 产生一个一致性hash环
        ConsistentHash<ServerInfo> consistentHash = 
        		new ConsistentHash<ServerInfo>(new ConsistentHash.HashFunction(), 1000, set);
        
        // 通过请求参数获取存储的机器信息
        System.out.println(consistentHash.get("hello").getIp());
        System.out.println(consistentHash.get("world").getIp());
    }
}
