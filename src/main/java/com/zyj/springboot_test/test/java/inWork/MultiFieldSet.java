package com.zyj.springboot_test.test.java.inWork;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 设置多域类
 * 
 * @author xiuhuwang
 * @date 2011.11.15
 * 
 */

public class MultiFieldSet implements Serializable {
    public final static String DELEMITER = "|";
    private int fieldNum;
    private HashMap<String, Integer> fieldMap = new HashMap<>();
    private String fields[];
    private Vector<String[]> records = new Vector<>();
    private int index;
    private String text;

    public MultiFieldSet(int fieldNum) {
        this.fieldNum = fieldNum;
        reset();
    }

    public MultiFieldSet(String s, int fieldNum) throws MultiFieldException {
        this.fieldNum = fieldNum;
        this.text = s;
        this.buildRecords();
    }

    public String getString() throws MultiFieldException {
        StringBuffer s = new StringBuffer();
        // 字段名
        for (int i = 0; i < fields.length; i++) {
            s.append(fields[i]).append(DELEMITER);
        }
        s.append("\n");
        // 记录
        beforeFirst();
        while (next()) {
            for (int i = 0; i < fields.length; i++) {
                s.append(getValue(i)).append(DELEMITER);
            }
            if (index < records.size() - 1) {
                s.append("\n");
            }
        }
        return new String(s);
    }

    public int getFieldNum() {
        return fieldNum;
    }

    public void setFieldNum(int fieldNum) {
        this.fieldNum = fieldNum;
    }

    public void setFieldName(String[] fieldName) throws Exception {
        if (fieldName == null || fieldName.length != fieldNum) {
            throw new Exception("字段数量与定义不符");
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fieldName[i];
            fieldMap.put(fields[i], Integer.valueOf(i));
        }
    }

    public boolean next() {
        index++;
        return index < records.size();
    }

    public void beforeFirst() {
        index = -1;
    }

    private void reset() {
        fields = new String[fieldNum];
        fieldMap.clear();
        beforeFirst();
    }

    public String getValue(String fieldName) throws MultiFieldException {
        return getValue(getFieldIndex(fieldName));
    }

    public String getValue(int idx) throws MultiFieldException {
        String[] buf = getBuffer();
        if (idx < 0 || idx >= buf.length) {
            throw new MultiFieldException("字段下标超界:" + idx);
        }
        String ret = buf[idx];
        if (ret != null) {
            ret = ret.replaceAll("　", "").trim();
        }
        return ret;
    }

    public int getRecordNum() {
        return records.size();
    }

    public int getIndex() {
        return index;
    }

    public void setValue(String fieldName, String v) throws MultiFieldException {
        setValue(getFieldIndex(fieldName), v);
    }

    public void setValue(int idx, String v) throws MultiFieldException {
        String[] buf = getBuffer();
        if (idx < 0 || idx >= buf.length) {
            throw new MultiFieldException("字段下标超界:" + idx);
        }
        buf[idx] = v;
    }

    public int append() {
        String[] rec = new String[fieldNum];
        for (int i = 0; i < rec.length; i++) {
            rec[i] = "";
        }
        records.add(rec);
        index = records.size() - 1;
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void buildRecords() throws MultiFieldException {
        if (fieldNum < 1) {
            return;
        }
        String s = text.trim();
        int tail = s.endsWith(DELEMITER) ? 1 : 0; // 检查尾部是否有分割符
        s = StringUtils.replace(s, DELEMITER, " " + DELEMITER + " ");
        StringTokenizer st = new StringTokenizer(s, DELEMITER);
        int n = st.countTokens() - tail;
        if (n % fieldNum > 0) {
            throw new MultiFieldException("字符串数应该是字段数的整倍数-字符串" + n + "个，字段" + fieldNum + "个");
        }
        reset();
        int count = 0;
        for (int i = 0; i < n; i++) {
            String token = st.nextToken().trim();
            count = i % fieldNum;
            // 标题行
            if (i < fieldNum) {
                fields[count] = token;
                fieldMap.put(token, Integer.valueOf(count));
                continue;
            }
            // 新插一行
            if (count == 0) {
                append();
            }
            // 插入字段内容
            String[] r = getBuffer();
            r[count] = token;
        }
        this.beforeFirst();
    }

    private String[] getBuffer() {
        if (index < 0 || index >= records.size()) {
            return new String[0];
        }
        return records.get(index);
    }

    private int getFieldIndex(String field) throws MultiFieldException {
        Integer idx = fieldMap.get(field);
        if (idx == null) {
            throw new MultiFieldException("字段'" + field + "'未定义");
        }
        return idx.intValue();
    }
}
