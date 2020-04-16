package com.zyj.springboot_test.test.mysql.mybatis.model;

public class SingleTable {
    private String key1;
    private int key2;
    private String key3;
    private String key_part1;
    private String key_part2;
    private String key_part3;
    private String common_field;

    public SingleTable(String key1, int key2, String key3, String key_part1, String key_part2, String key_part3, String common_field) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key_part1 = key_part1;
        this.key_part2 = key_part2;
        this.key_part3 = key_part3;
        this.common_field = common_field;
    }

    public SingleTable() {
    }
}
