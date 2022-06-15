package com.zyj.springboot_test.test.java.inWork;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;


/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class AIPGSignature {
    
    public static final String SHA1_RSA = "SHA1withRSA";
    public static final String SM3_SM2 = "SM3withSM2";
    
    private static final String BC = "BC";
    
    private String encoding = "GBK";
    private String algorithm = SHA1_RSA;
    private boolean useBC = false;
    static{
    	  try{
    	   Security.addProvider(new BouncyCastleProvider());
    	  }catch(Exception e){
    	   e.printStackTrace();
    	  }
    }

    public AIPGSignature(String algorithm){
        this.algorithm = algorithm;
    }
    
    public AIPGSignature(String algorithm, String encoding){
        this.algorithm = algorithm;
        this.encoding = encoding;
    }
    
    public AIPGSignature(String algorithm, boolean useBC){
        this.algorithm = algorithm;
        this.useBC = useBC;
    }
    
    public AIPGSignature(String algorithm, String encoding, boolean useBC){
        this.algorithm = algorithm;
        this.encoding = encoding;
        this.useBC = useBC;
    }
    
    public AIPGSignature(Provider prvd) {
		// TODO Auto-generated constructor stub
	}

	/**
     * @param msg  签名报文
     * @param keyfile
     * @param pass
     * @return  签名字符串
     * @throws Exception
     */
    public String signMsg(String msg, File p12File, String pass) throws Exception{
        String signedStr = null;
       
        KeyStore ks = useBC ? KeyStore.getInstance("PKCS12", BC) : KeyStore.getInstance("PKCS12");
        FileInputStream fiKeyFile = new FileInputStream(p12File);
        try {
            ks.load(fiKeyFile, pass.toCharArray());
        } catch (Exception ex) {
            if (fiKeyFile != null)
                fiKeyFile.close();
            throw new Exception("加载证书时出错" + ex.getMessage(), ex);
        }
        Enumeration<String> myEnum = ks.aliases();
        String keyAlias = null;
        PrivateKey prikey = null;
        while (myEnum.hasMoreElements()) {
            keyAlias = (String) myEnum.nextElement();
            if (ks.isKeyEntry(keyAlias)) {
                prikey = (PrivateKey) ks.getKey(keyAlias, pass.toCharArray());
                break;
            }
        }
        if (prikey == null) {
            throw new Exception("没有找到匹配私钥");
        }
        
        Signature sign = useBC ? Signature.getInstance(algorithm, BC) : Signature.getInstance(algorithm);
        sign.initSign(prikey);
        sign.update(msg.getBytes(encoding));
        byte signed[] = sign.sign();
        byte sign_asc[] = new byte[signed.length * 2];
        hex2Ascii(signed.length, signed, sign_asc);
        signedStr = new String(sign_asc);
        
        return signedStr;
    }
    
    /**
     * 
     * @param signedStr  签名字符串
     * @param msg   报文
     * @param pkfile
     * @return
     * @throws AIPGException
     */
    public boolean verifyMsg(String signedStr, String msg, File crtfile) throws Exception{
        FileInputStream certfile = new FileInputStream(crtfile);
        CertificateFactory cf = useBC ? CertificateFactory.getInstance("X.509", BC) 
                : CertificateFactory.getInstance("X.509");
        X509Certificate x509cert = null;
        try {
            x509cert = (X509Certificate) cf.generateCertificate(certfile);
        } catch (Exception ex) {
            if (certfile != null)
                certfile.close();
            throw new Exception("加载证书时出错" + ex.getMessage(), ex);
        }
        PublicKey pubkey = (PublicKey) x509cert.getPublicKey();
        Signature verify = useBC ? Signature.getInstance(algorithm, BC):Signature.getInstance(algorithm);
        verify.initVerify(pubkey);
        //TEST-----------证书位数
//        RSAPublicKeySpec keySpec = KeyFactory.getInstance("RSA", BC).getKeySpec(pubkey, RSAPublicKeySpec.class);
//        System.out.println(keySpec.getModulus().toString(2).length());
        //TEST-----------
        signedStr = signedStr.toLowerCase();
        byte signeddata[] = new byte[signedStr.length() / 2];
        ascii2Hex(signedStr.length(), signedStr.getBytes(encoding), signeddata);
        verify.update(msg.getBytes(encoding));
        return verify.verify(signeddata);
    }
    
    /**
     * 将十六进制数据转换成ASCII字符串
     * @param len  十六进制数据长度
     * @param data_in 待转换的十六进制数据
     * @param data_out 已转换的ASCII字符串
     */
    private static void hex2Ascii(int len, byte data_in[], byte data_out[]) {
        byte temp1[] = new byte[1];
        byte temp2[] = new byte[1];
        for (int i = 0, j = 0; i < len; i++) {
            temp1[0] = data_in[i];
            temp1[0] = (byte) (temp1[0] >>> 4);
            temp1[0] = (byte) (temp1[0] & 0x0f);
            temp2[0] = data_in[i];
            temp2[0] = (byte) (temp2[0] & 0x0f);
            if (temp1[0] >= 0x00 && temp1[0] <= 0x09) {
                (data_out[j]) = (byte) (temp1[0] + '0');
            } else if (temp1[0] >= 0x0a && temp1[0] <= 0x0f) {
                (data_out[j]) = (byte) (temp1[0] + 0x57);
            }
            if (temp2[0] >= 0x00 && temp2[0] <= 0x09) {
                (data_out[j + 1]) = (byte) (temp2[0] + '0');
            } else if (temp2[0] >= 0x0a && temp2[0] <= 0x0f) {
                (data_out[j + 1]) = (byte) (temp2[0] + 0x57);
            }
            j += 2;
        }
    }

    /**
     * 将ASCII字符串转换成十六进制数据
     * @param len ASCII字符串长度
     * @param data_in 待转换的ASCII字符串
     * @param data_out 已转换的十六进制数据
     */
    private static void ascii2Hex(int len, byte data_in[], byte data_out[]) {
        byte temp1[] = new byte[1];
        byte temp2[] = new byte[1];
        for (int i = 0, j = 0; i < len; j++) {
            temp1[0] = data_in[i];
            temp2[0] = data_in[i + 1];
            if (temp1[0] >= '0' && temp1[0] <= '9') {
                temp1[0] -= '0';
                temp1[0] = (byte) (temp1[0] << 4);
                temp1[0] = (byte) (temp1[0] & 0xf0);
            } else if (temp1[0] >= 'a' && temp1[0] <= 'f') {
                temp1[0] -= 0x57;
                temp1[0] = (byte) (temp1[0] << 4);
                temp1[0] = (byte) (temp1[0] & 0xf0);
            }
            if (temp2[0] >= '0' && temp2[0] <= '9') {
                temp2[0] -= '0';
                temp2[0] = (byte) (temp2[0] & 0x0f);
            } else if (temp2[0] >= 'a' && temp2[0] <= 'f') {
                temp2[0] -= 0x57;
                temp2[0] = (byte) (temp2[0] & 0x0f);
            }
            data_out[j] = (byte) (temp1[0] | temp2[0]);
            i += 2;
        }
    }
    
    public static void main(String[] args) throws Exception{
        AIPGSignature signature = new AIPGSignature(AIPGSignature.SHA1_RSA, false);
        String signedStr = signature.signMsg("ABCDEFG", new File("config/200581000000859jfb.p12"), "111111");
        System.out.println(signedStr);
        System.out.println(signature.verifyMsg(signedStr, "ABCDEFG", new File("config/200581000000859.cer")));
    }
    
}