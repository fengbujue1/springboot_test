package com.zyj.springboot_test.test.redis.jetCache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.zyj.springboot_test.SpringbootTestApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author 周赟吉
 * @Date 2023/4/12 11:12
 * @Description :
 *  试一下jetCache的简单用法---------测试失败，springboot启动不起来 ........
 */
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = {"com.zhouyunji"})
@ComponentScan(basePackages = {"com.zhouyunji"})
@SpringBootApplication
public class MainTest {
    /**
     * 1分钟
     */
    @CreateCache(name="BGW_SSPLIT:",expire = 60)
    public static Cache<String,String> splitCache;

    public static void main(String[] args) {
        SpringApplication.run(MainTest.class, args);
        splitCache.put("123", "456");
    }
}
