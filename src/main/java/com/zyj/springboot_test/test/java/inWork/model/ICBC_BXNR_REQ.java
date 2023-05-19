package com.zyj.springboot_test.test.java.inWork.model;
/**
 * @author jiangyuan
 * @since：2022/10/31 14:48
 * @description: 票据签收
 * */

import java.io.Serializable;
import java.util.List;

public class ICBC_BXNR_REQ implements Serializable {
    public Class getReqClass() {
        return ICBC_BXNR_REQ.class;
    }
    public String getBTX() {
        return "BXNR";
    }
    public Class<ICBC_BXNR_REQ> getResponseClass() {
        return ICBC_BXNR_REQ.class;
    }

    public String name;

    public boolean isNeedEncrypt() {
        return false;
    }
    public String getMethod() {
        return "POST";
    }
}
