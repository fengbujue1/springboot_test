package com.zyj.springboot_test.test.java.inWork;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.security.Provider;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class DemoUtil {
	public static Provider prvd = null;

	static{
	    System.out.println("初始化BC"  +  System.getProperty("addprovider"));
		prvd = new BouncyCastleProvider();  //密码不能用特殊符号
		if("true".equals(System.getProperty("addprovider")) && Security.getProvider("BC") == null){
		    System.out.println("未找到·");
		    Security.addProvider(prvd);
		}
	}

	public static InfoReq makeReq(String trxcod){
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(DemoConfig.merchantid + String.format("-%016d",System.currentTimeMillis()));
		info.setUSER_NAME(DemoConfig.username);
		info.setUSER_PASS(DemoConfig.userpass);
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION("03");
	    info.setMERCHANT_ID(DemoConfig.merchantid);

		return info;
	}

	public static String getNow(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	public static String getRandomString(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * @param xmlMsg   待签名报文
	 * @return  签名后的报文
	 * @throws AIPGException
	 */
	public static String buildSignedXml(String xmlMsg) throws AIPGException{
		if(xmlMsg == null){
			throw new AIPGException("传入的加签报文为空");
		}
		String IDD_STR="<SIGNED_MSG></SIGNED_MSG>";
		if(xmlMsg.indexOf(IDD_STR) == -1){
			throw new AIPGException("找不到签名信息字段");
		}
		String strMsg = xmlMsg.replaceAll(IDD_STR, "");
		AIPGSignature signature = new AIPGSignature(AIPGSignature.SHA1_RSA, false);
		/*AIPGSignature signature = new AIPGSignature(AIPGSignature.SM3_SM2, true);*/
		String signedStr = null;
        try {
            signedStr = signature.signMsg(strMsg, new File(DemoConfig.pathpfx), DemoConfig.pfxpass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AIPGException("3999", "签名失败");
        }
		String strRnt = xmlMsg.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedStr + "</SIGNED_MSG>");
		return strRnt;
	}

	/**
	 * @param xmlMsg  返回报文
	 * @return
	 * @throws AIPGException
	 */
	public static boolean verifyXml(String xmlMsg) throws AIPGException{
		if(xmlMsg == null){
			throw new AIPGException("传入的验签报文为空");
		}
		int pre = xmlMsg.indexOf("<SIGNED_MSG>");
		int suf = xmlMsg.indexOf("</SIGNED_MSG>");
		if(pre == - 1 || suf == -1 || pre >= suf){
			throw new AIPGException("找不到签名信息");
		}
		String signedStr = xmlMsg.substring(pre + 12, suf);
		String msgStr = xmlMsg.substring(0, pre) + xmlMsg.substring(suf + 13);
		AIPGSignature signature = new AIPGSignature(AIPGSignature.SHA1_RSA, false);
	/*	AIPGSignature signature = new AIPGSignature(AIPGSignature.SM3_SM2, true);//国密
*/		try {
            return signature.verifyMsg(signedStr, msgStr, new File(DemoConfig.pathcer));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AIPGException("3999", "验签失败");
        }
	}

    public static void main(String[] args) throws Exception{
        String xmlMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
				"<AIPG>\n" +
				"   <AHQUERYREQ>\n" +
				"      <ACCTNO>202110151634281677550</ACCTNO>\n" +
				"      <STARTDAY>20220520</STARTDAY>\n" +
				"      <ENDDAY>20220520</ENDDAY>\n" +
				"   </AHQUERYREQ>\n" +
				"   <INFO>\n" +
				"      <TRX_CODE>300001</TRX_CODE>\n" +
				"      <VERSION>04</VERSION>\n" +
				"      <DATA_TYPE>2</DATA_TYPE>\n" +
				"      <LEVEL>9</LEVEL>\n" +
				"      <MERCHANT_ID>200584000021065</MERCHANT_ID>\n" +
				"      <USER_NAME>20058400002106506</USER_NAME>\n" +
				"      <USER_PASS>111111</USER_PASS>\n" +
				"      <REQ_SN>2005840000210653000000245171</REQ_SN>\n" +
				"      <SIGNED_MSG>b611f1544e61f68c23822c905f2550521ff5df7d34bdc41169afaad4004fb864181e060e197f637c60e78f1cb6419572614c3593e08aeffda1772effd74715ff92e3aef5f11d36c62c6c4bd274660a95dcac79d5521f1f3476936d00e8801fa904c40af57b212be8b5180f8e01962d2d8d6df3d747a519d102ea8b2de9b1e3a2</SIGNED_MSG>\n" +
				"   </INFO>\n" +
				"</AIPG>";
        System.out.println(verifyXml(xmlMsg));
		xmlMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n" +
				"<AIPG>\n" +
				"   <AHQUERYREQ>\n" +
				"      <ACCTNO>202110151634281677550</ACCTNO>\n" +
				"      <STARTDAY>20220520</STARTDAY>\n" +
				"      <ENDDAY>20220520</ENDDAY>\n" +
				"   </AHQUERYREQ>\n" +
				"   <INFO>\n" +
				"      <TRX_CODE>300001</TRX_CODE>\n" +
				"      <VERSION>04</VERSION>\n" +
				"      <DATA_TYPE>2</DATA_TYPE>\n" +
				"      <LEVEL>9</LEVEL>\n" +
				"      <MERCHANT_ID>200584000021065</MERCHANT_ID>\n" +
				"      <USER_NAME>20058400002106506</USER_NAME>\n" +
				"      <USER_PASS>111111</USER_PASS>\n" +
				"      <REQ_SN>2005840000210653000000245171</REQ_SN>\n" +
				"		<SIGNED_MSG></SIGNED_MSG>"+
				"   </INFO>\n" +
				"</AIPG>";
        System.out.println(buildSignedXml(xmlMsg));

    }
}
