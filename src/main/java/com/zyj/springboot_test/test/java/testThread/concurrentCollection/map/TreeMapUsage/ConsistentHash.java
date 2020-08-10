package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.TreeMapUsage;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * 一致性hash算法的实现
 */
public class ConsistentHash<T> {
	// hash函数
    private final HashFunction hashFunction;
    
    // 产生的虚拟节点数
    private final int numberOfReplicas;
    
    // 一致性hash环
    private final SortedMap<Integer, ServerInfo> circle = new TreeMap<Integer, ServerInfo>();
    
    // 构建一致性hash环
    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
                          Collection<ServerInfo> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (ServerInfo node : nodes) {
            add(node);
        }
    }

    public void add(ServerInfo node) {
        circle.put(hashFunction.hash(node.getIp()), node);
        // 将单个服务虚拟化成指定的虚拟个数，通过ip地址+1的方式
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.getIp() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public ServerInfo get(Object argKey) {
        if (circle.isEmpty()) {
            return null;
        }
        // 通过对参数key进行hash算法，获得hash值
        int hash = hashFunction.hash(argKey);
        if (!circle.containsKey(hash)) {
        	// 无法直接获得节点，则往右查找服务器的虚拟节点
            SortedMap<Integer, ServerInfo> tailMap = circle.tailMap(hash);
            // tailMap不存在，则从头开始，返回第一个key（原点），即第一个虚拟节点上。
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    static class HashFunction {
        int hash(Object key) {
            // md5加密后，hashcode
            return MD5.md5(key.toString()).hashCode();
        }
    }
}
