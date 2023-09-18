package com.zyj.springboot_test.test.java.inWork.icbc;

import com.alibaba.fastjson.JSON;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcApiException;
import com.icbc.api.IcbcConstants;
import com.icbc.api.IcbcResponse;
import com.icbc.api.internal.util.fastjson.serializer.SerializerFeature;
import com.icbc.api.internal.util.internal.util.fastjson.JSONObject;
import com.zyj.springboot_test.util.TextFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {


        ICBC_BXAD_REQ request = new ICBC_BXAD_REQ();

        ICBC_BXAD_REQ.Content bizContent = new ICBC_BXAD_REQ.Content();
        // 请对照接口文档用bizContent.setxxx()方法对业务上送数据进行赋值
        bizContent.setTransCode("QREVOBILL");
        bizContent.setTranDate("20230605");
        bizContent.setTranTime("150801001");
        bizContent.setLanguage("zh_CN");
        //bizContent.setfSeqNo(UUID.randomUUID().toString());
        bizContent.setfSeqNo("100691664151788702826496");
        bizContent.setAcc_no("3202008709200072972");
        //业务类型：NES_AC_01-提示承兑申请 NES_RB_01-提示收票申请 NES_GA_01-保证申请 NES_ED_01-背书转让申请 NES_PD_01-质押申请 NES_PD_03-质押解除申请
        // NES_PS_01-提示付款申请 NES_RC_01-追索申请 NES_RC_03-同意清偿申请 NES_DI_01-贴现申请 NES_MC_01-不可转让撤销申请
        bizContent.setBusiness_type("NES_AC_01");
        bizContent.setPack_amt_bgn(0);
        bizContent.setPack_amt_end(1000000000);
        bizContent.setApply_date_bgn("20230501");
        bizContent.setApply_date_end("20230630");
        bizContent.setCd_tp("0");
        bizContent.setDue_date_bgn("20230101");
        bizContent.setDue_date_end("20231231");
        //  bizContent.setBat_serial_no("KYA235235011724288");

        List<ICBC_BXAD_REQ.Rd> rdList = new ArrayList<>();
        ICBC_BXAD_REQ.Rd rd = new ICBC_BXAD_REQ.Rd();
        rd.setPackNo("");
        rdList.add(rd);

        bizContent.setRd(rdList);
        request.setBizContent(bizContent);
        request.setServiceUrl("https://apipcs3.dccnet.com.cn" + "/api/mybank/enterprise/bill/qrevobill/V1");

        DefaultIcbcClient client = new DefaultIcbcClient(
                "11000000000000003955",
                IcbcConstants.SIGN_TYPE_CA_SM_ICBC,
                "8e612934dcc35ad7de54ab3aa035f87ae86435abec017811dfcb6961f5968494",
                IcbcConstants.CHARSET_UTF8,
                IcbcConstants.FORMAT_JSON,
                null,
                null,
                null,
                "MIICWjCCAf6gAwIBAgIGANVaWF55MAwGCCqBHM9VAYN1BQAwSTELMAkGA1UEBhMCY24xHzAdBgNVBAoMFnNtMmNvcnRlc3QuaWNiYy5jb20uY24xGTAXBgNVBAMMEHNtMnJvb3RjYWNvcnRlc3QwHhcNMjMwNDE3MDk1OTAyWhcNMjgwNDE0MTU1OTU5WjBaMQswCQYDVQQGEwJjbjEfMB0GA1UECgwWc20yY29ydGVzdC5pY2JjLmNvbS5jbjENMAsGA1UECwwEMzIwMjEbMBkGA1UEAwwSdGVzdDEyLnkuMzIwMi4wMjAxMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE57By7Wk5lh4cPVeWyQerEzdfkPZ4iOSzfGkdlFi4RqjDabkZInm/FfWbGRjGL/P4EgNbzOVvPqCHY2m2SlAsK6OBvjCBuzAfBgNVHSMEGDAWgBR+tzPmCUjiEkTQaf/xyKhWGYVwBzAJBgNVHRMEAjAAMGEGA1UdHwRaMFgwVqBUoFKkUDBOMRAwDgYDVQQDDAdjcmw5MDI4MQwwCgYDVQQLDANjcmwxHzAdBgNVBAoMFnNtMmNvcnRlc3QuaWNiYy5jb20uY24xCzAJBgNVBAYTAmNuMAsGA1UdDwQEAwIHgDAdBgNVHQ4EFgQUJ5f5XP1rsH2bvCWMJwbFK6slNNAwDAYIKoEcz1UBg3UFAANIADBFAiBByNaTZfNcEEaJU31QjRVcquWj5OMFKjRBcKfPMzGoAwIhAP2lwdaUXXo+8KrjrMoPzJoqcidLN8+LH1AMdNXjDUI2",
                null);
        client.setIcbc_ca("MIICRjCCAemgAwIBAgIGANVaWEirMAwGCCqBHM9VAYN1BQAwSTELMAkGA1UEBhMCY24xHzAdBgNVBAoMFnNtMmNvcnRlc3QuaWNiYy5jb20uY24xGTAXBgNVBAMMEHNtMnJvb3RjYWNvcnRlc3QwHhcNMjIwNzI3MDIzMzU3WhcNMzIwNzI0MDIzMzU3WjBFMQswCQYDVQQGEwJjbjEfMB0GA1UECgwWc20yY29ydGVzdC5pY2JjLmNvbS5jbjEVMBMGA1UEAwwMaWNiY19hcGlwXzAxMFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEe0L4n3HNwOXSLHrOW2d6mGW2J7naWk5Ty6VLvzyBkVO/RD1l4MHmwdTbuLvIyAmC+QQu9UnZDEr5w6wMqIo9/qOBvjCBuzAfBgNVHSMEGDAWgBR+tzPmCUjiEkTQaf/xyKhWGYVwBzAJBgNVHRMEAjAAMGEGA1UdHwRaMFgwVqBUoFKkUDBOMRAwDgYDVQQDDAdjcmw5MDIyMQwwCgYDVQQLDANjcmwxHzAdBgNVBAoMFnNtMmNvcnRlc3QuaWNiYy5jb20uY24xCzAJBgNVBAYTAmNuMAsGA1UdDwQEAwIHgDAdBgNVHQ4EFgQU0t7MWcePZGoHTr+QoPAXESdWXngwDAYIKoEcz1UBg3UFAANJADBGAiEAvBRJyXKTidl8zMNOVBdkT6GKrFaZWY7LPzt/+9wwdWsCIQCuD3oc21xUvcNrMEgkDHjn2tsNAv+89n5lozXlsCIwMA==");
        IcbcResponse resp = null;
        try {
            System.out.println(JSON.toJSONString(request));
            resp = client.execute(request);
            ICBC_BXAD_RES icbc_bxad_res = (ICBC_BXAD_RES) resp;
            List<ICBC_BXAD_RES.Rd> rd1 = icbc_bxad_res.getRd();
            System.out.println(rd1.size());
        } catch (IcbcApiException e) {
            e.printStackTrace();
        }
        String response = JSONObject.toJSONString(resp, SerializerFeature.WriteMapNullValue);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>发送报文结束,响应报文如下<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(response);

    }
}
