package com.zyj.springboot_test.test.mysql.shardingsphere;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
//        insert(dataSource);
//        selectByTime(dataSource);
        selectById(dataSource);
    }

    public static void insert(DataSource dataSource) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        String sql = "insert into detail " +
                "(seri_no_1, seri_no_2, order_id, goods_name,insert_time,opposite_account,receive_money)" +
                " values (?, ?, ?, ?,?,?,?)";
        int serialNo = 245;
        //1.预处理SQL
        long start = System.currentTimeMillis();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        for(int i = 0; i < 50; i++) {

            pstmt.setInt(1, serialNo);
            pstmt.setString(2, String.valueOf(serialNo));
            pstmt.setString(3, String.valueOf(serialNo));
            pstmt.setString(4, "goods_" + serialNo);
            String time = "2022011";
            int day = new Random().nextInt(10);
            time = time + day;
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date parse = df.parse(time);
            pstmt.setTimestamp(5, new java.sql.Timestamp(parse.getTime()));
            pstmt.setString(6, "account_" + serialNo);
            pstmt.setBigDecimal(7, new BigDecimal(20));
            pstmt.addBatch();

        }
        pstmt.executeBatch();
        connection.commit();
        long end = System.currentTimeMillis();
        System.out.println("结束处理，耗时:" +(end-start)/1000+"秒");
        pstmt.close();
    }

    public static void selectByTime(DataSource dataSource) throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        String sql = "select * from detail where insert_time>=  ? and insert_time<=  ?";
//        String sql = "select * from detail where seri_no_1 = 233";
//        String sql = "select * from detail where UNIX_TIMESTAMP(insert_time) between UNIX_TIMESTAMP(?) AND UNIX_TIMESTAMP(?) ";
//        String sql = "select * from detail where insert_time = ? ";
        //1.预处理SQL
        PreparedStatement pstmt = connection.prepareStatement(sql);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
        java.util.Date parse1 = df.parse("20220116 000000");
        java.sql.Timestamp date1 = new java.sql.Timestamp(parse1.getTime());
        pstmt.setTimestamp(1,date1);
        java.util.Date parse2 = df.parse("20220117 235959");
        java.sql.Timestamp date2 = new java.sql.Timestamp(parse2.getTime());
        pstmt.setTimestamp(2,date2);
        ResultSet resultSet = pstmt.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count++;
            String seri_no_1 = resultSet.getString("seri_no_1");
            System.out.println("seri_no_1:" + seri_no_1);
        }
        System.out.println("count:" + count);
        connection.commit();
        pstmt.close();

    }

    public static void selectById(DataSource dataSource) throws Exception {
        long start = System.currentTimeMillis();
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        String sql = "select * from detail d join order1 o on d.order_id = o.order_id " +
                "where d.seri_no_1 = 245 and d.insert_time>=  ? and d.insert_time<=  ? " +
                "limit 5,2";
        //1.预处理SQL
        PreparedStatement pstmt = connection.prepareStatement(sql);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
        java.util.Date parse1 = df.parse("20220116 000000");
        java.sql.Timestamp date1 = new java.sql.Timestamp(parse1.getTime());
        pstmt.setTimestamp(1,date1);
        java.util.Date parse2 = df.parse("20220117 235959");
        java.sql.Timestamp date2 = new java.sql.Timestamp(parse2.getTime());
        pstmt.setTimestamp(2,date2);
        ResultSet resultSet = pstmt.executeQuery();
        int count = 0;
        while (resultSet.next()) {
            count++;
            String seri_no_1 = resultSet.getString("seri_no_1");
            String id = resultSet.getString("id");
            System.out.println("id:" + id);
            System.out.println("seri_no_1:" + seri_no_1);
        }
        System.out.println("count:" + count);
        System.out.println("cost:" + (System.currentTimeMillis() - start) + "ms");
        connection.close();
        pstmt.close();

    }
}
