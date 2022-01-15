package com.zyj.springboot_test.test.mysql.shardingsphere;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.sql.Date;
import java.util.Collection;
import java.util.Properties;

/**
 * Description:
 *
 * @author 周赟吉
 * @since 2022/1/14
 */
public class TableShardingAlgorithm implements StandardShardingAlgorithm<Date> {

    @Override
    public void setProps(Properties properties) {

    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public String getType() {
        return "GROUP_LOT_RELATION";
    }

    @Override
    public void init() {
    }

    @Override
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Date> preciseShardingValue) {
        String log = "";
        System.out.println("table PreciseShardingAlgorithm ");
        // 真实节点
        tableNames.forEach((item) -> {
            String log2;
            log2 = String.format("actual node table:{" + item + "}");
            System.out.println(log2);
        });

        log = String.format("logic table name:{},rout column:{}", preciseShardingValue.getLogicTableName(), preciseShardingValue.getColumnName());
        System.out.println(log);

        //精确分片
        log = String.format("column value:{}", preciseShardingValue.getValue().toString());
        System.out.println(log);


        String tb_name = preciseShardingValue.getLogicTableName() + "_";


        // 根据当前日期 来 分库分表
        Date date = preciseShardingValue.getValue();
        String year = String.format("%tY", date);
        String mon =String.format("%tm", date);
        String day = String.format("%td", date);


        // 选择表
        tb_name = tb_name + year + mon + day;
        System.out.println("tb_name:" + tb_name);

        for (String each : tableNames) {
            System.out.println("select:" + each);
            if (each.equals(tb_name)) {
                return each;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        throw new RuntimeException("123");
    }
}
