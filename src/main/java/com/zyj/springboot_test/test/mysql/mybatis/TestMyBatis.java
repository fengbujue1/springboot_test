package com.zyj.springboot_test.test.mysql.mybatis;

import com.zyj.springboot_test.test.mysql.mybatis.mapper.SingleTableMapper;
import com.zyj.springboot_test.test.mysql.mybatis.model.SingleTable;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class TestMyBatis {
    public static void main(String[] args){
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = null;
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = null;
        try {

            for (int i = 50; i < 10000; i++) {
                sqlSession = sqlSessionFactory.openSession();
                SingleTableMapper mapper = sqlSession.getMapper(SingleTableMapper.class);
                mapper.insert1(new SingleTable("key1_" + i, i, "key3_" + i, "key_part1_" + i, "key_part2_" + i, "key_part3_" + i, "common_field"));
                mapper.insert2(new SingleTable("key1_" + i, i, "key3_" + i, "key_part1_" + i, "key_part2_" + i, "key_part3_" + i, "common_field"));
                sqlSession.commit();
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

        }



    }
}
