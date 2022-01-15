package com.zyj.springboot_test.test.mysql.shardingsphere;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author 周赟吉
 * @since 2022/1/13
 */
public class Sharding_Time_spring_namespase {
    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("sharding.xml");
        DataSource dataSource = (DataSource)classPathXmlApplicationContext.getBean("shardingDataSource");
        insert(dataSource);
//        select(dataSource);
    }

    public static void insert(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        String sql = "insert into detail " +
                "(seri_no_1, seri_no_2, order_id, goods_name,date,opposite_account,receive_money)" +
                " values (?, ?, ?, ?,?,?,?)";
        int serialNo = 0;
        for(int i = 0; i < 10; i++) {
            serialNo++;
            //1.预处理SQL
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, serialNo);
            pstmt.setString(2, String.valueOf(serialNo));
            pstmt.setString(3, String.valueOf(serialNo));
            pstmt.setString(4, "goods_" + serialNo);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //从前端或者自己模拟一个日期格式，转为String即可
            String dateStr = format.format(date);
            pstmt.setDate(5, new java.sql.Date(date.getTime()));
            pstmt.setString(6, "account_" + serialNo);
            pstmt.setBigDecimal(7, new BigDecimal(20));
            long start = System.currentTimeMillis();
            pstmt.execute();
            long end = System.currentTimeMillis();
            System.out.println("结束批处理，耗时:" +(end-start)/1000+"秒");
            System.out.println("处理完了：" + 1 + "条数据");
            connection.commit();
            pstmt.close();
        }
    }

    public static void select(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        String sql = "select * from detail where seri_no_1>1 and seri_no_1<8";
        //1.预处理SQL
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            String seri_no_1 = resultSet.getString("seri_no_1");
            System.out.println("seri_no_1:" + seri_no_1);
        }
        connection.commit();
        pstmt.close();

    }
}
