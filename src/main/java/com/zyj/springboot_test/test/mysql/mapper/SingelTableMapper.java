package com.zyj.springboot_test.test.mysql.mapper;

import com.zyj.springboot_test.test.mysql.model.SingleTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper()
public interface SingelTableMapper {

    @Insert("insert into single_table(key1,key2,key3,key_part1,key_part2,key_part3,common_field) values(#{key1},#{key2},#{key3},#{key_part1},#{key_part2},#{key_part3},#{common_field})")
    int insert1(SingleTable singleTable);

    @Insert("insert into single_table2(key1,key2,key3,key_part1,key_part2,key_part3,common_field) values(#{key1},#{key2},#{key3},#{key_part1},#{key_part2},#{key_part3},#{common_field})")
    int insert2(SingleTable singleTable);

}
