package com.zyj.springboot_test.test.mysql.refreshDB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Description:
 *
 * @author 周赟吉
 * @since 2022/1/12
 */
public class DeleteData {

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
        String sql = "delete from detail_1e_inno where id < ?";
        connection.setAutoCommit(false);
        for(int i = 0; i < 670000; i++) {
            //1.预处理SQL
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(i*5000));
            long start = System.currentTimeMillis();
            System.out.println("开始批处理");
            pstmt.executeBatch(); //执行批处理
            long end = System.currentTimeMillis();
            System.out.println("结束批处理，耗时:" +(end-start)/1000+"秒");
            System.out.println("处理完了：" + 5000 + "条数据");
            connection.commit();
            pstmt.close();
        }
    }
}
