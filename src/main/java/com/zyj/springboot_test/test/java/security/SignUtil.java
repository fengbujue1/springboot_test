package com.zyj.springboot_test.test.java.security;

import com.google.common.io.BaseEncoding;
import com.zyj.springboot_test.util.XMLUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.jdom.Element;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @Author 周赟吉
 * @Date 2022/5/19 13:56
 * @Description :加密工具
 */
public class SignUtil {
    /**
     * rsa内部签名
     */
    public static String signSHA1RSA(byte[] privateKey, String content, String charset) {
        byte[] messageBytes = content.getBytes(Charset.forName(charset));
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey pk = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(pk);
            signature.update(messageBytes);
            return BaseEncoding.base64().encode(signature.sign()).replace("\r\n", "");
        } catch (Exception e) {
            throw new IllegalArgumentException("SHA1 RSA sign failed : " + e.getMessage(), e);
        }
    }

    /**
     * 使用RSA公钥进行验签，其中摘要算法为SHA1算法
     * 将响应报文去掉SIGNED_MSG域值换成空行用SHA1算哈希值，将该哈希值与上一步获得的验签值进行比较，一致则验证通过。
     * @param charset   内容的编码方式
     * @param publicKey 验签的公钥
     * @param text 原始响应报文
     * @return 签名匹配是返回true，否则返回false
     * @throws IllegalArgumentException 私钥不对，以及RSA SHA1算法不存在时抛出
     */
    public static boolean verifySHA1RSASign(byte[] publicKey, String text, String charset) throws Exception {
        Element root = XMLUtil.getRootElement(text);
        String sign = root.getChild("INFO").getChildTextTrim("SIGNED_MSG");
        int start = text.indexOf("<SIGNED_MSG>");
        int end = text.indexOf("</SIGNED_MSG>") + "</SIGNED_MSG>".length();
        String substring = text.substring(start, end);
        text = text.replace(substring, "");
        return rsaCheckSign(publicKey, sign, text, charset);
    }

    /**
     * 使用公钥验签
     *
     * @param publicKeyPath 公钥文件地址
     * @param sign          base64转码后的秘文
     * @param content       原始明文
     * @param charset       编码
     * @return 是否验签成功
     */
    private static boolean rsaCheckSign(String publicKeyPath, String sign, String content, String charset) throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File(publicKeyPath));
        return rsaCheckSign(bytes, sign, content, charset);
    }

    /**
     * 使用公钥验签
     * @param publicKey  公钥文件的字节
     * @param sign base64转码后的秘文
     * @param content 原始明文
     * @param charset 编码
     * @return 是否验签成功
     */
    public static boolean rsaCheckSign(byte[] publicKey, String sign, String content, String charset) throws UnsupportedEncodingException {
        byte[] messageBytes = content.getBytes(Charset.forName(charset));
        byte[] signBytes = Base64.decodeBase64(sign.getBytes(charset));
        try {

            String s = "";
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pk = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pk);
            signature.update(messageBytes);
            System.out.println("messageBytes:" + Arrays.toString(messageBytes));
            System.out.println( Arrays.toString(signBytes));
            return signature.verify(signBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("SHA1 RSA verify sign failed : " + e.getMessage(), e);
        }
    }
}
