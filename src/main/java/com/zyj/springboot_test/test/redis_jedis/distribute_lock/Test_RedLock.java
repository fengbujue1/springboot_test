package com.zyj.springboot_test.test.redis_jedis.distribute_lock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

public class Test_RedLock {
    public static void main(String[] args) {
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL);
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("perredis://182.92.211.22:6379");

        RedissonClient redisson = Redisson.create(config);

        redisson.getRedLock();
    }
}
