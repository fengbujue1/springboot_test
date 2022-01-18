package com.zyj.springboot_test.test.mysql.refreshDB;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Description:
 *
 * @author 周赟吉
 * @since 2022/1/12
 */
public class InsertData {

    public static void main(String[] args) throws Exception {

//        String url = "jdbc:mysql://localhost:3306/TEST";
//        String username = "fengbujue235";
//        String password = "zhouyunji1316";
        String url = "jdbc:mysql://47.96.0.106:3306/TEST?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "fengbujue234";
        String password = "zhouyunji1315";
        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, username, password);
        //流水号
        int serialNo = 0;
        String sql = "insert into detail" +
                "(seri_no_1, seri_no_2, order_id, goods_name,date,opposite_account,receive_money)" +
                " values (?, ?, ?, ?,?,?,?)";
        connection.setAutoCommit(false);
        //1.预处理SQL
        PreparedStatement pstmt = connection.prepareStatement(sql);

        for(int k = 0; k < 100; k++) {
            String time = "2022011";
            int i = new Random().nextInt(10);
            time = time + i;
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date parse = df.parse(time);
            serialNo++;
            pstmt.setString(1, String.valueOf(k));
            pstmt.setString(1, String.valueOf(serialNo));
            pstmt.setString(2, String.valueOf(serialNo));
            pstmt.setString(3, String.valueOf(serialNo));
            pstmt.setString(4, "goods_" + serialNo);
            pstmt.setTimestamp(5, new Timestamp(parse.getTime()));
            pstmt.setString(6, "account_" + serialNo);
            pstmt.setBigDecimal(7, new BigDecimal(20));
            //加入批处理
            pstmt.addBatch();
        }
        long start = System.currentTimeMillis();
        System.out.println("开始批处理");
        pstmt.executeBatch(); //执行批处理
        long end = System.currentTimeMillis();
        System.out.println("结束批处理，耗时:" +(end-start)/1000+"秒");
//            System.out.println("处理完了：" + serialNo + "条数据");
        System.out.println("处理完了：" + 5000 + "条数据");
        connection.commit();
//            if (i % 10 == 0) {
//                connection.commit();
//            }
        pstmt.close();
    }
}
