package com.zyj.springboot_test.test.mysql.shardingsphere;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Description:
 *
 * @author 周赟吉
 * @since 2022/1/13
 */
public class Sharding_Time_API {
    public static void main(String[] args) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第 1 个数据源
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://47.96.0.106:3306/TEST?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSource1.setUsername("fengbujue234");
        dataSource1.setPassword("zhouyunji1315");
        dataSourceMap.put("TEST", dataSource1);


        // 配置 detail 表规则
        ShardingTableRuleConfiguration orderTableRuleConfig =
                new ShardingTableRuleConfiguration("detail", "TEST.detail_${1..2}");

        // 配置分库策略
//        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id",
//                "dbShardingAlgorithm"));

        // 配置分表策略
        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("date",
                "tableShardingAlgorithm"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(orderTableRuleConfig);

        // 配置分库算法
//        Properties dbShardingAlgorithmrProps = new Properties();
//        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "detail_${id % 2}");
//        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm",
//                new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));

        // 配置分表算法
        Properties tableShardingAlgorithmrProps = new Properties();
        tableShardingAlgorithmrProps.setProperty("strategy", "STANDARD");
        tableShardingAlgorithmrProps.setProperty("algorithmClassName", "com.zyj.springboot_test.test.mysql.shardingsphere.TableShardingAlgorithm");
        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm",
                new ShardingSphereAlgorithmConfiguration("INTERVAL", tableShardingAlgorithmrProps));//时间分片算法

        // 创建 ShardingSphereDataSource
        DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap,
                Collections.singleton(shardingRuleConfig), new Properties());
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
            pstmt.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
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
