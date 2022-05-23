package com.zyj.springboot_test.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/7
 */
public class XMLUtil {

    public static void main(String[] args) throws Exception {
        test1();
        test2();
    }

    public static Element getRootElement(String s) throws Exception {
        StringReader in = null;

        Element var5;
        try {
            in = new StringReader(s);
            SAXBuilder builder = new SAXBuilder(false);
            Document document = builder.build(in);
            Element ret = document.getRootElement();
            var5 = ret;
        } finally {
            if (in != null) {
                in.close();
            }

        }

        return var5;
    }

    public static String buildText(Document document) throws IOException {
        XMLOutputter out = new XMLOutputter();
        Format format = Format.getPrettyFormat();
        format.setIndent("   ");
        format.setExpandEmptyElements(true);
        format.setLineSeparator("\r\n");
        out.setFormat(format);
        return out.outputString(document);
    }

    public static String buildText(Element element) throws IOException {
        XMLOutputter out = new XMLOutputter();
        Format format = Format.getPrettyFormat();
        format.setIndent("   ");
        format.setExpandEmptyElements(true);
        format.setLineSeparator("\r\n");
        out.setFormat(format);
        return out.outputString(element);
    }


    public static void test2() throws Exception {

    }

    public static void test1() throws Exception {
        String text = getText();

        Element root = getRootElement(text);
        Element response = root.getChild("Transaction_Body").getChild("response");
        Iterator it = response.getChildren("LIST1").iterator();
        while (it.hasNext()) {
            Element element = (Element)it.next();
            // 发生金额
            String dhamt = element.getChildTextTrim("DHAmt");
            String cr_hpnam = element.getChildTextTrim("Cr_HpnAm");
            System.out.println("DHAmt:" + dhamt);
            System.out.println("Cr_HpnAm:" + cr_hpnam);
            System.out.println("------------------------");
        }
    }

    private static String getText() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Transaction>\n" +
                "\t<Transaction_Header>\n" +
                "\t\t<SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN>\n" +
                "\t\t<SYS_MSG_LEN><![CDATA[]]></SYS_MSG_LEN>\n" +
                "\t\t<SYS_RECV_TIME><![CDATA[20220321083003115]]></SYS_RECV_TIME>\n" +
                "\t\t<SYS_RESP_TIME><![CDATA[20220321083003235]]></SYS_RESP_TIME>\n" +
                "\t\t<SYS_PKG_STS_TYPE><![CDATA[01]]></SYS_PKG_STS_TYPE>\n" +
                "\t\t<SYS_TX_STATUS><![CDATA[00]]></SYS_TX_STATUS>\n" +
                "\t\t<SYS_RESP_CODE><![CDATA[000000000000]]></SYS_RESP_CODE>\n" +
                "\t\t<SYS_RESP_DESC_LEN><![CDATA[]]></SYS_RESP_DESC_LEN>\n" +
                "\t\t<SYS_RESP_DESC><![CDATA[成功]]></SYS_RESP_DESC>\n" +
                "\t\t<SYS_EVT_TRACE_ID><![CDATA[1010010481647822603969930]]></SYS_EVT_TRACE_ID>\n" +
                "\t\t<SYS_SND_SERIAL_NO><![CDATA[0000000000]]></SYS_SND_SERIAL_NO>\n" +
                "\t\t<TOTAL_PAGE>1</TOTAL_PAGE>\n" +
                "\t\t<TOTAL_REC>4</TOTAL_REC>\n" +
                "\t\t<CURR_TOTAL_PAGE>1</CURR_TOTAL_PAGE>\n" +
                "\t\t<CURR_TOTAL_REC><![CDATA[4]]></CURR_TOTAL_REC>\n" +
                "\t\t<STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID>\n" +
                "\t\t<SYS_TX_CODE><![CDATA[P1CMSERB4]]></SYS_TX_CODE>\n" +
                "\t\t<CHNL_CUST_NO><![CDATA[SZ44200009211363301]]></CHNL_CUST_NO>\n" +
                "\t\t<tran_response>\n" +
                "\t\t\t<status><![CDATA[COMPLETE]]></status>\n" +
                "\t\t</tran_response>\n" +
                "\t</Transaction_Header>\n" +
                "\t<Transaction_Body>\n" +
                "\t\t<response>\n" +
                "\t\t\t<FILE_LIST_PACK p_type=\"G\">\n" +
                "\t\t\t\t<FILE_NUM><![CDATA[0]]></FILE_NUM>\n" +
                "\t\t\t\t<FILE_INFO p_type=\"G\">\n" +
                "\t\t\t\t\t<FILE_NAME><![CDATA[]]></FILE_NAME>\n" +
                "\t\t\t\t</FILE_INFO>\n" +
                "\t\t\t</FILE_LIST_PACK>\n" +
                "\t\t\t<Rvl_Rcrd_Num><![CDATA[4]]></Rvl_Rcrd_Num>\n" +
                "\t\t\t<LIST1 p_type=\"G\">\n" +
                "\t\t\t\t<ExoStm_Py_Rmrk><![CDATA[]]></ExoStm_Py_Rmrk>\n" +
                "\t\t\t\t<AccNo><![CDATA[44201518300052505841]]></AccNo>\n" +
                "\t\t\t\t<AccNo_Nm><![CDATA[中兴通讯股份有限公司]]></AccNo_Nm>\n" +
                "\t\t\t\t<Txn_Dt><![CDATA[20220321]]></Txn_Dt>\n" +
                "\t\t\t\t<Txn_Dtl_No><![CDATA[1]]></Txn_Dtl_No>\n" +
                "\t\t\t\t<CcyCd><![CDATA[156]]></CcyCd>\n" +
                "\t\t\t\t<CshEx_Cd><![CDATA[1]]></CshEx_Cd>\n" +
                "\t\t\t\t<CCBS_Opr_No><![CDATA[]]></CCBS_Opr_No>\n" +
                "\t\t\t\t<DHAmt><![CDATA[0.00]]></DHAmt>\n" +
                "\t\t\t\t<Cr_HpnAm><![CDATA[2199.10]]></Cr_HpnAm>\n" +
                "\t\t\t\t<CCBS_Txn_CgyCd><![CDATA[02]]></CCBS_Txn_CgyCd>\n" +
                "\t\t\t\t<Bal>2199.10</Bal>\n" +
                "\t\t\t\t<CCBS_TxnSrlNo><![CDATA[442000007226PJFSBPM]]></CCBS_TxnSrlNo>\n" +
                "\t\t\t\t<Vchr_TpCd><![CDATA[]]></Vchr_TpCd>\n" +
                "\t\t\t\t<Vchr_No><![CDATA[]]></Vchr_No>\n" +
                "\t\t\t\t<Smy><![CDATA[结息]]></Smy>\n" +
                "\t\t\t\t<Smy_Cd><![CDATA[0246]]></Smy_Cd>\n" +
                "\t\t\t\t<Rmrk><![CDATA[]]></Rmrk>\n" +
                "\t\t\t\t<CntprtAcc><![CDATA[44201518300052515217]]></CntprtAcc>\n" +
                "\t\t\t\t<Cntrprt_AccNm><![CDATA[中兴通讯集团财务有限公司]]></Cntrprt_AccNm>\n" +
                "\t\t\t\t<Mdf_Doc_Inf_IndCd><![CDATA[]]></Mdf_Doc_Inf_IndCd>\n" +
                "\t\t\t\t<CshPl_Bal><![CDATA[5850122.01]]></CshPl_Bal>\n" +
                "\t\t\t\t<UpDep_Amt><![CDATA[5847922.91]]></UpDep_Amt>\n" +
                "\t\t\t\t<LwrLvl_Dep_Amt><![CDATA[0.00]]></LwrLvl_Dep_Amt>\n" +
                "\t\t\t\t<Supr_Lnd_Amt><![CDATA[0.00]]></Supr_Lnd_Amt>\n" +
                "\t\t\t\t<LwrLvl_Lnd_Amt><![CDATA[0.00]]></LwrLvl_Lnd_Amt>\n" +
                "\t\t\t\t<Inr_Dep_Acm><![CDATA[0.00]]></Inr_Dep_Acm>\n" +
                "\t\t\t\t<Inr_Od_Acm><![CDATA[0.00]]></Inr_Od_Acm>\n" +
                "\t\t\t\t<Inr_Agrm_Dep_Acm><![CDATA[0.00]]></Inr_Agrm_Dep_Acm>\n" +
                "\t\t\t\t<ValDt><![CDATA[20220321]]></ValDt>\n" +
                "\t\t\t\t<Txn_Tm><![CDATA[232330]]></Txn_Tm>\n" +
                "\t\t\t\t<Hst_Txn_CD><![CDATA[A05821902]]></Hst_Txn_CD>\n" +
                "\t\t\t\t<Rvrs_IndCd><![CDATA[05]]></Rvrs_IndCd>\n" +
                "\t\t\t\t<Rvrs_Dtl_No><![CDATA[0]]></Rvrs_Dtl_No>\n" +
                "\t\t\t\t<CDBank_Nm><![CDATA[建行深圳华侨城支行]]></CDBank_Nm>\n" +
                "\t\t\t\t<Agnc_Entp_AccNo><![CDATA[]]></Agnc_Entp_AccNo>\n" +
                "\t\t\t\t<Agnc_EntNm><![CDATA[]]></Agnc_EntNm>\n" +
                "\t\t\t\t<CstPty_TxnSrlNo><![CDATA[]]></CstPty_TxnSrlNo>\n" +
                "\t\t\t\t<IntRt><![CDATA[0.00000]]></IntRt>\n" +
                "\t\t\t\t<DpBkNm><![CDATA[中国建设银行股份有限公司深圳华侨城支行]]></DpBkNm>\n" +
                "\t\t\t\t<Srpls_Can_Od_Lmt><![CDATA[0.00]]></Srpls_Can_Od_Lmt>\n" +
                "\t\t\t\t<Od_Amt><![CDATA[0.00]]></Od_Amt>\n" +
                "\t\t\t\t<DbtCrDrcCd><![CDATA[2]]></DbtCrDrcCd>\n" +
                "\t\t\t\t<Bnk_Only1_ID_ID><![CDATA[442000007226PJFSBPM1]]></Bnk_Only1_ID_ID>\n" +
                "\t\t\t\t<Tres_Txn_Tp><![CDATA[4]]></Tres_Txn_Tp>\n" +
                "\t\t\t\t<CADBank_BrNo><![CDATA[442000007]]></CADBank_BrNo>\n" +
                "\t\t\t\t<Grp_CshPl_Sign_IndCd><![CDATA[]]></Grp_CshPl_Sign_IndCd>\n" +
                "\t\t\t\t<Dtl_TpCd><![CDATA[02]]></Dtl_TpCd>\n" +
                "\t\t\t\t<Stm_Dt><![CDATA[20220320]]></Stm_Dt>\n" +
                "\t\t\t\t<TrdPt_AccNo><![CDATA[44201518300052515217]]></TrdPt_AccNo>\n" +
                "\t\t\t\t<TrdPt_AccNm><![CDATA[中兴通讯集团财务有限公司]]></TrdPt_AccNm>\n" +
                "\t\t\t\t<TrdPt_Py_BrNo><![CDATA[442000007]]></TrdPt_Py_BrNo>\n" +
                "\t\t\t\t<Agnc_PymtBnk_Nm><![CDATA[建行深圳华侨城支行]]></Agnc_PymtBnk_Nm>\n" +
                "\t\t\t\t<Dep_Txn_Medm_ID><![CDATA[]]></Dep_Txn_Medm_ID>\n" +
                "\t\t\t\t<Rsrv_Fld_11><![CDATA[]]></Rsrv_Fld_11>\n" +
                "\t\t\t</LIST1>\n" +
                "\t\t\t<LIST1 p_type=\"G\">\n" +
                "\t\t\t\t<ExoStm_Py_Rmrk><![CDATA[]]></ExoStm_Py_Rmrk>\n" +
                "\t\t\t\t<AccNo><![CDATA[44201518300052505841]]></AccNo>\n" +
                "\t\t\t\t<AccNo_Nm><![CDATA[中兴通讯股份有限公司]]></AccNo_Nm>\n" +
                "\t\t\t\t<Txn_Dt><![CDATA[20220321]]></Txn_Dt>\n" +
                "\t\t\t\t<Txn_Dtl_No><![CDATA[2]]></Txn_Dtl_No>\n" +
                "\t\t\t\t<CcyCd><![CDATA[156]]></CcyCd>\n" +
                "\t\t\t\t<CshEx_Cd><![CDATA[1]]></CshEx_Cd>\n" +
                "\t\t\t\t<CCBS_Opr_No><![CDATA[]]></CCBS_Opr_No>\n" +
                "\t\t\t\t<DHAmt><![CDATA[2199.10]]></DHAmt>\n" +
                "\t\t\t\t<Cr_HpnAm><![CDATA[0.00]]></Cr_HpnAm>\n" +
                "\t\t\t\t<CCBS_Txn_CgyCd><![CDATA[02]]></CCBS_Txn_CgyCd>\n" +
                "\t\t\t\t<Bal>0.00</Bal>\n" +
                "\t\t\t\t<CCBS_TxnSrlNo><![CDATA[442000007226PJFSBPM]]></CCBS_TxnSrlNo>\n" +
                "\t\t\t\t<Vchr_TpCd><![CDATA[]]></Vchr_TpCd>\n" +
                "\t\t\t\t<Vchr_No><![CDATA[]]></Vchr_No>\n" +
                "\t\t\t\t<Smy><![CDATA[资金归集]]></Smy>\n" +
                "\t\t\t\t<Smy_Cd><![CDATA[0134]]></Smy_Cd>\n" +
                "\t\t\t\t<Rmrk><![CDATA[资金归集]]></Rmrk>\n" +
                "\t\t\t\t<CntprtAcc><![CDATA[44201518300052515217]]></CntprtAcc>\n" +
                "\t\t\t\t<Cntrprt_AccNm><![CDATA[中兴通讯集团财务有限公司]]></Cntrprt_AccNm>\n" +
                "\t\t\t\t<Mdf_Doc_Inf_IndCd><![CDATA[]]></Mdf_Doc_Inf_IndCd>\n" +
                "\t\t\t\t<CshPl_Bal><![CDATA[5850122.01]]></CshPl_Bal>\n" +
                "\t\t\t\t<UpDep_Amt><![CDATA[5850122.01]]></UpDep_Amt>\n" +
                "\t\t\t\t<LwrLvl_Dep_Amt><![CDATA[0.00]]></LwrLvl_Dep_Amt>\n" +
                "\t\t\t\t<Supr_Lnd_Amt><![CDATA[0.00]]></Supr_Lnd_Amt>\n" +
                "\t\t\t\t<LwrLvl_Lnd_Amt><![CDATA[0.00]]></LwrLvl_Lnd_Amt>\n" +
                "\t\t\t\t<Inr_Dep_Acm><![CDATA[0.00]]></Inr_Dep_Acm>\n" +
                "\t\t\t\t<Inr_Od_Acm><![CDATA[0.00]]></Inr_Od_Acm>\n" +
                "\t\t\t\t<Inr_Agrm_Dep_Acm><![CDATA[0.00]]></Inr_Agrm_Dep_Acm>\n" +
                "\t\t\t\t<ValDt><![CDATA[20220321]]></ValDt>\n" +
                "\t\t\t\t<Txn_Tm><![CDATA[232330]]></Txn_Tm>\n" +
                "\t\t\t\t<Hst_Txn_CD><![CDATA[A05821902]]></Hst_Txn_CD>\n" +
                "\t\t\t\t<Rvrs_IndCd><![CDATA[05]]></Rvrs_IndCd>\n" +
                "\t\t\t\t<Rvrs_Dtl_No><![CDATA[0]]></Rvrs_Dtl_No>\n" +
                "\t\t\t\t<CDBank_Nm><![CDATA[建行深圳华侨城支行]]></CDBank_Nm>\n" +
                "\t\t\t\t<Agnc_Entp_AccNo><![CDATA[]]></Agnc_Entp_AccNo>\n" +
                "\t\t\t\t<Agnc_EntNm><![CDATA[]]></Agnc_EntNm>\n" +
                "\t\t\t\t<CstPty_TxnSrlNo><![CDATA[]]></CstPty_TxnSrlNo>\n" +
                "\t\t\t\t<IntRt><![CDATA[0.00000]]></IntRt>\n" +
                "\t\t\t\t<DpBkNm><![CDATA[中国建设银行股份有限公司深圳华侨城支行]]></DpBkNm>\n" +
                "\t\t\t\t<Srpls_Can_Od_Lmt><![CDATA[0.00]]></Srpls_Can_Od_Lmt>\n" +
                "\t\t\t\t<Od_Amt><![CDATA[0.00]]></Od_Amt>\n" +
                "\t\t\t\t<DbtCrDrcCd><![CDATA[1]]></DbtCrDrcCd>\n" +
                "\t\t\t\t<Bnk_Only1_ID_ID><![CDATA[442000007226PJFSBPM2]]></Bnk_Only1_ID_ID>\n" +
                "\t\t\t\t<Tres_Txn_Tp><![CDATA[2]]></Tres_Txn_Tp>\n" +
                "\t\t\t\t<CADBank_BrNo><![CDATA[442000007]]></CADBank_BrNo>\n" +
                "\t\t\t\t<Grp_CshPl_Sign_IndCd><![CDATA[]]></Grp_CshPl_Sign_IndCd>\n" +
                "\t\t\t\t<Dtl_TpCd><![CDATA[02]]></Dtl_TpCd>\n" +
                "\t\t\t\t<Stm_Dt><![CDATA[20220320]]></Stm_Dt>\n" +
                "\t\t\t\t<TrdPt_AccNo><![CDATA[44201518300052515217]]></TrdPt_AccNo>\n" +
                "\t\t\t\t<TrdPt_AccNm><![CDATA[中兴通讯集团财务有限公司]]></TrdPt_AccNm>\n" +
                "\t\t\t\t<TrdPt_Py_BrNo><![CDATA[442000007]]></TrdPt_Py_BrNo>\n" +
                "\t\t\t\t<Agnc_PymtBnk_Nm><![CDATA[建行深圳华侨城支行]]></Agnc_PymtBnk_Nm>\n" +
                "\t\t\t\t<Dep_Txn_Medm_ID><![CDATA[]]></Dep_Txn_Medm_ID>\n" +
                "\t\t\t\t<Rsrv_Fld_11><![CDATA[归集]]></Rsrv_Fld_11>\n" +
                "\t\t\t</LIST1>\n" +
                "\t\t\t<LIST1 p_type=\"G\">\n" +
                "\t\t\t\t<ExoStm_Py_Rmrk><![CDATA[]]></ExoStm_Py_Rmrk>\n" +
                "\t\t\t\t<AccNo><![CDATA[44201518300052505841]]></AccNo>\n" +
                "\t\t\t\t<AccNo_Nm><![CDATA[中兴通讯股份有限公司]]></AccNo_Nm>\n" +
                "\t\t\t\t<Txn_Dt><![CDATA[20220321]]></Txn_Dt>\n" +
                "\t\t\t\t<Txn_Dtl_No><![CDATA[3]]></Txn_Dtl_No>\n" +
                "\t\t\t\t<CcyCd><![CDATA[156]]></CcyCd>\n" +
                "\t\t\t\t<CshEx_Cd><![CDATA[1]]></CshEx_Cd>\n" +
                "\t\t\t\t<CCBS_Opr_No><![CDATA[]]></CCBS_Opr_No>\n" +
                "\t\t\t\t<DHAmt><![CDATA[0.00]]></DHAmt>\n" +
                "\t\t\t\t<Cr_HpnAm><![CDATA[21.21]]></Cr_HpnAm>\n" +
                "\t\t\t\t<CCBS_Txn_CgyCd><![CDATA[02]]></CCBS_Txn_CgyCd>\n" +
                "\t\t\t\t<Bal>21.21</Bal>\n" +
                "\t\t\t\t<CCBS_TxnSrlNo><![CDATA[4420000070N4PI8YNO7]]></CCBS_TxnSrlNo>\n" +
                "\t\t\t\t<Vchr_TpCd><![CDATA[]]></Vchr_TpCd>\n" +
                "\t\t\t\t<Vchr_No><![CDATA[]]></Vchr_No>\n" +
                "\t\t\t\t<Smy><![CDATA[结息]]></Smy>\n" +
                "\t\t\t\t<Smy_Cd><![CDATA[0246]]></Smy_Cd>\n" +
                "\t\t\t\t<Rmrk><![CDATA[]]></Rmrk>\n" +
                "\t\t\t\t<CntprtAcc><![CDATA[]]></CntprtAcc>\n" +
                "\t\t\t\t<Cntrprt_AccNm><![CDATA[]]></Cntrprt_AccNm>\n" +
                "\t\t\t\t<Mdf_Doc_Inf_IndCd><![CDATA[]]></Mdf_Doc_Inf_IndCd>\n" +
                "\t\t\t\t<CshPl_Bal><![CDATA[5850143.22]]></CshPl_Bal>\n" +
                "\t\t\t\t<UpDep_Amt><![CDATA[5850122.01]]></UpDep_Amt>\n" +
                "\t\t\t\t<LwrLvl_Dep_Amt><![CDATA[0.00]]></LwrLvl_Dep_Amt>\n" +
                "\t\t\t\t<Supr_Lnd_Amt><![CDATA[0.00]]></Supr_Lnd_Amt>\n" +
                "\t\t\t\t<LwrLvl_Lnd_Amt><![CDATA[0.00]]></LwrLvl_Lnd_Amt>\n" +
                "\t\t\t\t<Inr_Dep_Acm><![CDATA[0.00]]></Inr_Dep_Acm>\n" +
                "\t\t\t\t<Inr_Od_Acm><![CDATA[0.00]]></Inr_Od_Acm>\n" +
                "\t\t\t\t<Inr_Agrm_Dep_Acm><![CDATA[0.00]]></Inr_Agrm_Dep_Acm>\n" +
                "\t\t\t\t<ValDt><![CDATA[20220321]]></ValDt>\n" +
                "\t\t\t\t<Txn_Tm><![CDATA[235908]]></Txn_Tm>\n" +
                "\t\t\t\t<Hst_Txn_CD><![CDATA[A0181S05C]]></Hst_Txn_CD>\n" +
                "\t\t\t\t<Rvrs_IndCd><![CDATA[05]]></Rvrs_IndCd>\n" +
                "\t\t\t\t<Rvrs_Dtl_No><![CDATA[0]]></Rvrs_Dtl_No>\n" +
                "\t\t\t\t<CDBank_Nm><![CDATA[]]></CDBank_Nm>\n" +
                "\t\t\t\t<Agnc_Entp_AccNo><![CDATA[]]></Agnc_Entp_AccNo>\n" +
                "\t\t\t\t<Agnc_EntNm><![CDATA[]]></Agnc_EntNm>\n" +
                "\t\t\t\t<CstPty_TxnSrlNo><![CDATA[]]></CstPty_TxnSrlNo>\n" +
                "\t\t\t\t<IntRt><![CDATA[0.00000]]></IntRt>\n" +
                "\t\t\t\t<DpBkNm><![CDATA[中国建设银行股份有限公司深圳华侨城支行]]></DpBkNm>\n" +
                "\t\t\t\t<Srpls_Can_Od_Lmt><![CDATA[0.00]]></Srpls_Can_Od_Lmt>\n" +
                "\t\t\t\t<Od_Amt><![CDATA[0.00]]></Od_Amt>\n" +
                "\t\t\t\t<DbtCrDrcCd><![CDATA[2]]></DbtCrDrcCd>\n" +
                "\t\t\t\t<Bnk_Only1_ID_ID><![CDATA[4420000070N4PI8YNO73]]></Bnk_Only1_ID_ID>\n" +
                "\t\t\t\t<Tres_Txn_Tp><![CDATA[4]]></Tres_Txn_Tp>\n" +
                "\t\t\t\t<CADBank_BrNo><![CDATA[]]></CADBank_BrNo>\n" +
                "\t\t\t\t<Grp_CshPl_Sign_IndCd><![CDATA[]]></Grp_CshPl_Sign_IndCd>\n" +
                "\t\t\t\t<Dtl_TpCd><![CDATA[02]]></Dtl_TpCd>\n" +
                "\t\t\t\t<Stm_Dt><![CDATA[20220320]]></Stm_Dt>\n" +
                "\t\t\t\t<TrdPt_AccNo><![CDATA[]]></TrdPt_AccNo>\n" +
                "\t\t\t\t<TrdPt_AccNm><![CDATA[]]></TrdPt_AccNm>\n" +
                "\t\t\t\t<TrdPt_Py_BrNo><![CDATA[]]></TrdPt_Py_BrNo>\n" +
                "\t\t\t\t<Agnc_PymtBnk_Nm><![CDATA[]]></Agnc_PymtBnk_Nm>\n" +
                "\t\t\t\t<Dep_Txn_Medm_ID><![CDATA[]]></Dep_Txn_Medm_ID>\n" +
                "\t\t\t\t<Rsrv_Fld_11><![CDATA[]]></Rsrv_Fld_11>\n" +
                "\t\t\t</LIST1>\n" +
                "\t\t\t<LIST1 p_type=\"G\">\n" +
                "\t\t\t\t<ExoStm_Py_Rmrk><![CDATA[]]></ExoStm_Py_Rmrk>\n" +
                "\t\t\t\t<AccNo><![CDATA[44201518300052505841]]></AccNo>\n" +
                "\t\t\t\t<AccNo_Nm><![CDATA[中兴通讯股份有限公司]]></AccNo_Nm>\n" +
                "\t\t\t\t<Txn_Dt><![CDATA[20220321]]></Txn_Dt>\n" +
                "\t\t\t\t<Txn_Dtl_No><![CDATA[4]]></Txn_Dtl_No>\n" +
                "\t\t\t\t<CcyCd><![CDATA[156]]></CcyCd>\n" +
                "\t\t\t\t<CshEx_Cd><![CDATA[1]]></CshEx_Cd>\n" +
                "\t\t\t\t<CCBS_Opr_No><![CDATA[]]></CCBS_Opr_No>\n" +
                "\t\t\t\t<DHAmt><![CDATA[21.21]]></DHAmt>\n" +
                "\t\t\t\t<Cr_HpnAm><![CDATA[0.00]]></Cr_HpnAm>\n" +
                "\t\t\t\t<CCBS_Txn_CgyCd><![CDATA[02]]></CCBS_Txn_CgyCd>\n" +
                "\t\t\t\t<Bal>0.00</Bal>\n" +
                "\t\t\t\t<CCBS_TxnSrlNo><![CDATA[4420000070N4PI8YNO7]]></CCBS_TxnSrlNo>\n" +
                "\t\t\t\t<Vchr_TpCd><![CDATA[]]></Vchr_TpCd>\n" +
                "\t\t\t\t<Vchr_No><![CDATA[]]></Vchr_No>\n" +
                "\t\t\t\t<Smy><![CDATA[资金归集]]></Smy>\n" +
                "\t\t\t\t<Smy_Cd><![CDATA[0134]]></Smy_Cd>\n" +
                "\t\t\t\t<Rmrk><![CDATA[资金归集]]></Rmrk>\n" +
                "\t\t\t\t<CntprtAcc><![CDATA[44201518300052515217]]></CntprtAcc>\n" +
                "\t\t\t\t<Cntrprt_AccNm><![CDATA[中兴通讯集团财务有限公司]]></Cntrprt_AccNm>\n" +
                "\t\t\t\t<Mdf_Doc_Inf_IndCd><![CDATA[]]></Mdf_Doc_Inf_IndCd>\n" +
                "\t\t\t\t<CshPl_Bal><![CDATA[5850143.22]]></CshPl_Bal>\n" +
                "\t\t\t\t<UpDep_Amt><![CDATA[5850143.22]]></UpDep_Amt>\n" +
                "\t\t\t\t<LwrLvl_Dep_Amt><![CDATA[0.00]]></LwrLvl_Dep_Amt>\n" +
                "\t\t\t\t<Supr_Lnd_Amt><![CDATA[0.00]]></Supr_Lnd_Amt>\n" +
                "\t\t\t\t<LwrLvl_Lnd_Amt><![CDATA[0.00]]></LwrLvl_Lnd_Amt>\n" +
                "\t\t\t\t<Inr_Dep_Acm><![CDATA[0.00]]></Inr_Dep_Acm>\n" +
                "\t\t\t\t<Inr_Od_Acm><![CDATA[0.00]]></Inr_Od_Acm>\n" +
                "\t\t\t\t<Inr_Agrm_Dep_Acm><![CDATA[0.00]]></Inr_Agrm_Dep_Acm>\n" +
                "\t\t\t\t<ValDt><![CDATA[20220321]]></ValDt>\n" +
                "\t\t\t\t<Txn_Tm><![CDATA[235908]]></Txn_Tm>\n" +
                "\t\t\t\t<Hst_Txn_CD><![CDATA[A0181S05C]]></Hst_Txn_CD>\n" +
                "\t\t\t\t<Rvrs_IndCd><![CDATA[05]]></Rvrs_IndCd>\n" +
                "\t\t\t\t<Rvrs_Dtl_No><![CDATA[0]]></Rvrs_Dtl_No>\n" +
                "\t\t\t\t<CDBank_Nm><![CDATA[建行深圳华侨城支行]]></CDBank_Nm>\n" +
                "\t\t\t\t<Agnc_Entp_AccNo><![CDATA[]]></Agnc_Entp_AccNo>\n" +
                "\t\t\t\t<Agnc_EntNm><![CDATA[]]></Agnc_EntNm>\n" +
                "\t\t\t\t<CstPty_TxnSrlNo><![CDATA[]]></CstPty_TxnSrlNo>\n" +
                "\t\t\t\t<IntRt><![CDATA[0.00000]]></IntRt>\n" +
                "\t\t\t\t<DpBkNm><![CDATA[中国建设银行股份有限公司深圳华侨城支行]]></DpBkNm>\n" +
                "\t\t\t\t<Srpls_Can_Od_Lmt><![CDATA[0.00]]></Srpls_Can_Od_Lmt>\n" +
                "\t\t\t\t<Od_Amt><![CDATA[0.00]]></Od_Amt>\n" +
                "\t\t\t\t<DbtCrDrcCd><![CDATA[1]]></DbtCrDrcCd>\n" +
                "\t\t\t\t<Bnk_Only1_ID_ID><![CDATA[4420000070N4PI8YNO74]]></Bnk_Only1_ID_ID>\n" +
                "\t\t\t\t<Tres_Txn_Tp><![CDATA[2]]></Tres_Txn_Tp>\n" +
                "\t\t\t\t<CADBank_BrNo><![CDATA[442000007]]></CADBank_BrNo>\n" +
                "\t\t\t\t<Grp_CshPl_Sign_IndCd><![CDATA[]]></Grp_CshPl_Sign_IndCd>\n" +
                "\t\t\t\t<Dtl_TpCd><![CDATA[02]]></Dtl_TpCd>\n" +
                "\t\t\t\t<Stm_Dt><![CDATA[20220320]]></Stm_Dt>\n" +
                "\t\t\t\t<TrdPt_AccNo><![CDATA[]]></TrdPt_AccNo>\n" +
                "\t\t\t\t<TrdPt_AccNm><![CDATA[]]></TrdPt_AccNm>\n" +
                "\t\t\t\t<TrdPt_Py_BrNo><![CDATA[]]></TrdPt_Py_BrNo>\n" +
                "\t\t\t\t<Agnc_PymtBnk_Nm><![CDATA[]]></Agnc_PymtBnk_Nm>\n" +
                "\t\t\t\t<Dep_Txn_Medm_ID><![CDATA[]]></Dep_Txn_Medm_ID>\n" +
                "\t\t\t\t<Rsrv_Fld_11><![CDATA[归集]]></Rsrv_Fld_11>\n" +
                "\t\t\t</LIST1>\n" +
                "\t\t\t<TOTAL_PAGE>1</TOTAL_PAGE>\n" +
                "\t\t\t<TOTAL_REC>4</TOTAL_REC>\n" +
                "\t\t\t<CURR_TOTAL_PAGE>1</CURR_TOTAL_PAGE>\n" +
                "\t\t\t<CURR_TOTAL_REC><![CDATA[4]]></CURR_TOTAL_REC>\n" +
                "\t\t\t<STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID>\n" +
                "\t\t</response>\n" +
                "\t</Transaction_Body>\n" +
                "</Transaction>\n";
    }
}
