package com.zyj.springboot_test.test.spring.AOP.advisor;

import com.zyj.springboot_test.test.spring.AOP.advisor.Advisor;

import java.util.List;

/**
 * bean增强注册接口
 */
public interface AdvisorRegister {
    void registorAdvistor(Advisor advisor);

    List<Advisor> getAdvistors();
}
