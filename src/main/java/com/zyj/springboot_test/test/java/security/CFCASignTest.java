//package com.zyj.springboot_test.test.java.security;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import com.huifu.saturn.cfca.CFCASignature;
//import com.huifu.saturn.cfca.VerifyResult;
//import cfca.sadk.algorithm.common.Mechanism;
//import cfca.sadk.lib.crypto.JCrypto;
//import cfca.sadk.lib.crypto.Session;
//import cfca.sadk.util.Base64;
//import cfca.sadk.util.CertUtil;
//import cfca.sadk.util.KeyUtil;
//import cfca.sadk.util.Signature;
//import cfca.sadk.x509.certificate.X509Cert;
//import cfca.sadk.x509.certificate.X509CertVerifier;
//public class CFCASignTest {
//    public final static String SUCCESS = "0";
//    public final static String FAIL = "1";
//    private static final Log logger = LogFactory.getLog(CFCASignTest.class);
//
//    public static class Result{
//        private String code;
//        private String message;
//
//        public String getCode() {
//            return code;
//        }
//        public void setCode(String code) {
//            this.code = code;
//        }
//        public String getMessage() {
//            return message;
//        }
//        public void setMessage(String message) {
//            this.message = message;
//        }
//    }
//
//    public static void main(String[] args) {
//
//        String 	pfxFile	= "C:\\Users\\charle.chen\\Desktop\\510874汇付科技.pfx" ;
//        String 	publicCfcPath	= "C:\\Users\\charle.chen\\Desktop\\BOC.cer" ;
//        CFCASignTest cfca = new CFCASignTest();
//        //汇付加签
//        CFCASignTest.Result result = cfca.signCfca("汇付天下chinapnr", "GBK", pfxFile, "510874");
//        System.out.println(result.getCode() + " " + result.getMessage()); //加签结果， 及chkValue
//        //商户验签
//        CFCASignTest.Result result2 = cfca.verifySignCfca("100001", "汇付天下chinapnr", result.getMessage(), "GBK", publicCfcPath);
//        System.out.println(result2.getCode() + " " + result2.getMessage());  //验签结果
//    }
//    //CFCA验签20160908
//    public Result verifyMerSign(String merId, String signature, String charset, String trustCerPath) {
//        Result result = new Result();
//        VerifyResult verifyResult = CFCASignature.verifyMerSign( merId, signature, charset, trustCerPath);
//        result.setMessage(verifyResult.getMessage());
//        result.setCode(verifyResult.getCode());
//        return result ;
//    }
//
//    /**
//     *加签
//     */
//    public Result signCfca(String message, String charset, String privateCfcaPath, String privateCfcaPass){
//        Result result = new Result();
//
//        try{
//
//            JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
//            Session session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
//            String RSASignAlg = Mechanism.SHA1_RSA;
//
//            java.security.PrivateKey privateKey = KeyUtil.getPrivateKeyFromPFX(privateCfcaPath, privateCfcaPass);
//            X509Cert cert = CertUtil.getCertFromPFX(privateCfcaPath, privateCfcaPass);
//            Signature signature = new Signature();
//
//            byte[] chkValue = signature.p7SignMessageAttach(RSASignAlg, message.getBytes(charset),
//                    privateKey, cert, session);
//
//            if(chkValue != null){
//                ByteArrayOutputStream out = null;
//                GZIPOutputStream gzip = null;
//                try{
//                    out = new ByteArrayOutputStream();
//                    gzip = new GZIPOutputStream(out);
//                    gzip.write(chkValue);
//                    gzip.finish();
//                    gzip.flush();
//                    chkValue = out.toByteArray();
//                }finally{
//                    if(out != null){
//                        try {
//                            out.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if(gzip != null){
//                        try {
//                            gzip.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                result.setCode(CFCASignTest.SUCCESS);
//                result.setMessage(URLEncoder.encode(Base64.toBase64String(chkValue),charset));   //Hex.toHexString(chkValue)
//                return result;
//            }else{
//                result.setCode(CFCASignTest.FAIL);
//                result.setMessage("失败");
//                return result;
//            }
//        }catch (Exception e) {
//            logger.error("加签发生异常", e);
//            result.setCode(CFCASignTest.FAIL);
//            result.setMessage("异常");
//            return result;
//        }
//    }
//    /**
//     *验签
//     */
//    public Result verifySignCfca(String merId, String message, String chkValue, String charset,
//                                 String publicCfcaPath){
//        Result result = new Result();
//        ByteArrayOutputStream out = null;
//        GZIPInputStream gzip = null;
//        try{
//            JCrypto.getInstance().initialize(JCrypto.JSOFT_LIB, null);
//            Session session = JCrypto.getInstance().openSession(JCrypto.JSOFT_LIB);
//
//            Signature signature = new Signature();
//
//            byte[] chkValueByte = null;
//            if(chkValue.indexOf("%") >= 0){
//                chkValueByte = Base64.decode(URLDecoder.decode(chkValue, charset));
//            }else{
//                chkValueByte = Base64.decode(chkValue);
//
//            }
//
//
//            out = new ByteArrayOutputStream();
//            gzip = new GZIPInputStream(new ByteArrayInputStream(chkValueByte));
//            int b = 0;
//            while((b=gzip.read())!=-1){
//                out.write(b);
//            }
//            chkValueByte = out.toByteArray();
//
//            X509Cert cert = signature.getSignerX509CertFromP7SignData(chkValueByte);
//
//            X509CertVerifier.updateTrustCertsMap(publicCfcaPath);
//
//            if(!X509CertVerifier.validateCertSign(cert)){
//                result.setCode(CFCASignTest.FAIL);
//                result.setMessage("证书无效");
//                //return result;
//            }else if(!X509CertVerifier.verifyCertDate(cert)){
//                result.setCode(CFCASignTest.FAIL);
//                result.setMessage("证书过期");
//                return result;
//            }
//
//            if(signature.p7VerifyMessageAttach(chkValueByte, session)){
//                String merIdCert = signature.getSignerCert().getSubject();
//                if(!merId.equals(merIdCert.substring((merIdCert.indexOf('@') + 1), (merIdCert.indexOf('@') + 7)))){
//                    result.setCode(CFCASignTest.FAIL);
//                    result.setMessage("非商户证书");
//                    System.out.println(merIdCert.substring((merIdCert.indexOf('@') + 1), (merIdCert.indexOf('@') + 7)));
//                    System.out.println(merIdCert);
//                    return result;
//                }
//            }
//            if(!message.equals(new String(signature.getSourceData(), charset))){
//
//                System.out.println("message["+message+"]");
//                System.out.println("signature.getSourceData()["+signature.getSourceData()+"]");
//                result.setCode(CFCASignTest.FAIL);
//                result.setMessage("签名信息不正确");
//                return result;
//            }
//
//            result.setCode(CFCASignTest.SUCCESS);
//            result.setMessage("验签成功");
//            return result;
//        }catch (Exception e) {
//            logger.error("验签发生异常", e);
//            result.setCode(CFCASignTest.FAIL);
//            result.setMessage("验签异常");
//            return result;
//        }finally{
//            if(out != null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(gzip != null){
//                try {
//                    gzip.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//
//}