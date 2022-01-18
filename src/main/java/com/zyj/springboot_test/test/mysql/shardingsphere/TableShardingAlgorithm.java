package com.zyj.springboot_test.test.mysql.shardingsphere;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * Description:
 *  基于时间进行分片
 * @author 周赟吉
 * @since 2022/1/14
 */
public class TableShardingAlgorithm implements StandardShardingAlgorithm<Timestamp> {

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
    public String doSharding(Collection<String> tableNames, PreciseShardingValue<Timestamp> preciseShardingValue) {
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
        Timestamp dat = preciseShardingValue.getValue();
        Date date = new Date(dat.getTime());
        String year = String.format("%tY", date);
        String mon =String.format("%tm", date);
        String day = String.format("%td", date);


        // 选择表
        tb_name = tb_name + year + mon + day;
        System.out.println("tb_name:" + tb_name);

        long point1 = System.currentTimeMillis();
        //用contain方法要快很多！
        if (tableNames.contains(tb_name)) {
            long point2 = System.currentTimeMillis();
            System.out.println("cost1:" + (point2 - point1) );
            return tb_name;
        }
        //自己去挨个遍历就显得太蠢了
//        long point3 = System.currentTimeMillis();
//        for (String each : tableNames) {
//            System.out.println("select:" + each);
//            if (each.equals(tb_name)) {
//                long point4 = System.currentTimeMillis();
//                System.out.println("cost2:"+(point4-point3));
//                return each;
//            }
//        }

        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Timestamp> rangeShardingValue) {
        long start = System.currentTimeMillis();
        Range<Timestamp> valueRange = rangeShardingValue.getValueRange();
        HashSet<String> result = new HashSet<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        collection.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                //s的参数形式 detail_20220118 这样的形式，需要把逻辑表名去掉
                String[] s1 = s.split("_");
                try {
                    java.util.Date parse = df.parse(s1[1]);
                    Timestamp date = new Timestamp(parse.getTime());
                    //contain方法是个前开后开区间，所以需要额外加两个判断
                    if (valueRange.contains(date)||valueRange.lowerEndpoint().equals(date)||valueRange.upperEndpoint().equals(date)) {
                        result.add(s1[0] + "_" +  df.format(date));
                    }
                } catch (ParseException e) {
                    throw new RuntimeException("查询sql时，时间参数a转换异常:", e);
                }
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("query rooting cost:" + (end - start));
        return result;
    }
}
