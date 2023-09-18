package com.zyj.springboot_test.test.java.inWork.icbc;

import com.icbc.api.IcbcResponse;
import com.icbc.api.internal.util.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Author: lixiao
 * @Date: 2022/11/17 11:00
 * @Description: 提示付款申请
 */
public class ICBC_BXAD_RES extends IcbcResponse {
    /**
     * 票据包号：操作类型为企业信息报备时不返回
     */
    @JSONField(name = "err_code")
    private String errCode;
    /**
     * 票据包号：操作类型为企业信息报备时不返回
     */
    @JSONField(name = "err_msg")
    private String errMsg;

    /**
     * 循环区
     */
    @JSONField(name = "rd")
    private List<ICBC_BXAD_RES.Rd> rd;
    public static class Rd{
        /**
         * 批次号
         */
        @JSONField(name = "bat_serial_no")
        private String bat_serial_no;
        /**
         * 票据包号：操作类型为企业信息报备时不返回
         */
        @JSONField(name = "pack_no")
        private String pack_no;
        /**
         * 票据包号：操作类型为企业信息报备时不返回
         */
        @JSONField(name = "range_bgn")
        private String range_bgn;
        /**
         * 票据包号：操作类型为企业信息报备时不返回
         */
        @JSONField(name = "range_end")
        private String range_end;

        public String getBat_serial_no() {
            return bat_serial_no;
        }

        public void setBat_serial_no(String bat_serial_no) {
            this.bat_serial_no = bat_serial_no;
        }

        public String getPack_no() {
            return pack_no;
        }

        public void setPack_no(String pack_no) {
            this.pack_no = pack_no;
        }

        public String getRange_bgn() {
            return range_bgn;
        }

        public void setRange_bgn(String range_bgn) {
            this.range_bgn = range_bgn;
        }

        public String getRange_end() {
            return range_end;
        }

        public void setRange_end(String range_end) {
            this.range_end = range_end;
        }
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<Rd> getRd() {
        return rd;
    }

    public void setRd(List<Rd> rd) {
        this.rd = rd;
    }
}
