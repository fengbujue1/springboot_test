package com.zyj.springboot_test.test.java.inWork;


import java.io.Serializable;

/**
 * 参数的配置，入网商户对接通联通的商户必须找相应的分公司技术颁发唯一的系统对接参数
 * 如，商户号，用户名，密码，私钥证书
 **/
public class DemoConfig implements Serializable {
	public static String merchantidjfb = "200604000002185";
	public static String usernamejfb = "20060400000218504";
	public static String pathpfxjfb = "config/20060400000218504.p12";
	public static String merchantid = "200604000007293";//200604000006146系统对接的商户号,找通联的客户经理分配
	public static String url = "https://172.16.1.11:8443/aipg/ProcessServlet";//对接的测试接口地址
	public static String testTranURLjfb="https://172.16.1.10:8443/merapi/ProcessServlet"; 
	public static String urlFileGet="https://172.16.1.11:8443/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1";//简单对账文件的接口地址 
	public static String username = "20060400000729304"; //200604000006146用户名 ,找通联的客户经理分配
	public static String userpass = "111111"; //用户密码,找通联的客户经理分配
/*	public static String pathpfx = "config/20060400000729304ec.p12";/国密证书,商户公钥证书上传到通联通平台名字命名 商户号+04ec.cer*/
	public static String pathpfx = "D:/3.company/lt/user-rsa.p12";
	public static String pfxpass = "12345678"; //私钥密码,找通联的客户经理分配
	public static String pathcer = "D:/3.company/lt/public-rsa.cer"; //通联公钥
	public static boolean needProxy= false;
	 public static final String  httpProxyIp = "";
	 public static final int     httpProxyPort = 0;
	
}
