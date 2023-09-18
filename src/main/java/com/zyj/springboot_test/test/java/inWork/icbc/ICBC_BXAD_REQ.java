package com.zyj.springboot_test.test.java.inWork.icbc;

import com.icbc.api.AbstractIcbcRequest;
import com.icbc.api.BizContent;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: lixiao
 * @Date: 2022/11/17 10:57
 * @description: 提示付款申请
 */
public class ICBC_BXAD_REQ  extends AbstractIcbcRequest implements Serializable {


    @Override
    public Class getResponseClass() {
        return ICBC_BXAD_RES.class;
    }

    @Override
    public boolean isNeedEncrypt() {
        return false;
    }

    @Override
    public Class<? extends BizContent> getBizContentClass() {
        return Content.class;
    }

    @Override
    public String getMethod() {
        return "POST";
    }
    public static class Content implements BizContent{
        /**
         * 交易代码
         */
        @JSONField(name = "trans_code")
        private String transCode;
        /**
         * 交易日期：ERP系统产生，格式是yyyyMMdd
         */
        @JSONField(name = "tran_date")
        private String tranDate;
        /**
         * 交易时间：ERP系统产生，格式如HHmmssSSS，精确到毫秒
         */
        @JSONField(name = "tran_time")
        private String tranTime;
        /**
         * 语言：zh_CN简体中文，en_US英文，默认zh_CN
         */
        @JSONField(name = "language")
        private String language;
        /**
         * 指令包序列号：ERP系统产生，一个集团永远不能重复
         */
        @JSONField(name = "f_seq_no")
        private String  fSeqNo;

        @JSONField(name = "bat_serial_no")
        private String  bat_serial_no;

        @JSONField(name = "acc_no")
        private String  acc_no;

        @JSONField(name = "business_type")
        private String  business_type;

        @JSONField(name = "pack_amt_bgn")
        private int  pack_amt_bgn;

        @JSONField(name = "pack_amt_end")
        private int  pack_amt_end;

        @JSONField(name = "apply_date_bgn")
        private String  apply_date_bgn;

        @JSONField(name = "apply_date_end")
        private String  apply_date_end;

        @JSONField(name = "cd_tp")
        private String  cd_tp;

        @JSONField(name = "due_date_bgn")
        private String  due_date_bgn;

        @JSONField(name = "due_date_end")
        private String  due_date_end;

        @JSONField(name = "next_tag")
        private String  next_tag;


        /**
         * 票据列表：提示付款票据列表。
         */
        @JSONField(name = "rd")
        private List<Rd> rd;

        public String getTransCode() {
            return transCode;
        }

        public void setTransCode(String transCode) {
            this.transCode = transCode;
        }

        public String getTranDate() {
            return tranDate;
        }

        public void setTranDate(String tranDate) {
            this.tranDate = tranDate;
        }

        public String getTranTime() {
            return tranTime;
        }

        public void setTranTime(String tranTime) {
            this.tranTime = tranTime;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getfSeqNo() {
            return fSeqNo;
        }

        public void setfSeqNo(String fSeqNo) {
            this.fSeqNo = fSeqNo;
        }

        public String getBat_serial_no() {
            return bat_serial_no;
        }

        public void setBat_serial_no(String bat_serial_no) {
            this.bat_serial_no = bat_serial_no;
        }

        public String getAcc_no() {
            return acc_no;
        }

        public void setAcc_no(String acc_no) {
            this.acc_no = acc_no;
        }

        public String getBusiness_type() {
            return business_type;
        }

        public void setBusiness_type(String business_type) {
            this.business_type = business_type;
        }

        public int getPack_amt_bgn() {
            return pack_amt_bgn;
        }

        public void setPack_amt_bgn(int pack_amt_bgn) {
            this.pack_amt_bgn = pack_amt_bgn;
        }

        public int getPack_amt_end() {
            return pack_amt_end;
        }

        public void setPack_amt_end(int pack_amt_end) {
            this.pack_amt_end = pack_amt_end;
        }

        public String getApply_date_bgn() {
            return apply_date_bgn;
        }

        public void setApply_date_bgn(String apply_date_bgn) {
            this.apply_date_bgn = apply_date_bgn;
        }

        public String getApply_date_end() {
            return apply_date_end;
        }

        public void setApply_date_end(String apply_date_end) {
            this.apply_date_end = apply_date_end;
        }

        public String getCd_tp() {
            return cd_tp;
        }

        public void setCd_tp(String cd_tp) {
            this.cd_tp = cd_tp;
        }

        public String getDue_date_bgn() {
            return due_date_bgn;
        }

        public void setDue_date_bgn(String due_date_bgn) {
            this.due_date_bgn = due_date_bgn;
        }

        public String getDue_date_end() {
            return due_date_end;
        }

        public void setDue_date_end(String due_date_end) {
            this.due_date_end = due_date_end;
        }

        public String getNext_tag() {
            return next_tag;
        }

        public void setNext_tag(String next_tag) {
            this.next_tag = next_tag;
        }

        public List<Rd> getRd() {
            return rd;
        }

        public void setRd(List<Rd> rd) {
            this.rd = rd;
        }
    }

    public static  class Rd{
        @JSONField(name = "pack_no")
        private String  packNo;

        public String getPackNo() {
            return packNo;
        }

        public void setPackNo(String packNo) {
            this.packNo = packNo;
        }
    }


}
