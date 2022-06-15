package com.zyj.springboot_test.test.java.security;

import com.google.common.io.BaseEncoding;
import com.zyj.springboot_test.util.XMLUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.jdom.Element;

import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @Author 周赟吉
 * @Date 2022/5/19 13:56
 * @Description :加密工具
 */
public class SignUtil {
    /**
     * pkcs 8 秘钥
     * rsa内部签名
     */
    public static String signSHA1RSA_pkcs8(byte[] privateKey, String content, String charset) {
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

    public static String signSHA1RSA_pkcs12(byte[] privateKeyBytes, String content, String charset) throws Exception {
        byte[] messageBytes = content.getBytes(Charset.forName(charset));
        KeyStore ks = KeyStore.getInstance("PKCS12");
        InputStream ksStream = new ByteArrayInputStream(privateKeyBytes);
        ks.load(ksStream, "12345678".toCharArray());
        String keyAlias = null;
        //解析证书，必须有别名
        Enumeration<String> aliases = ks.aliases();
        if (aliases.hasMoreElements()) {
            keyAlias = aliases.nextElement();
        }
        //解析私钥
        PrivateKey pk = (PrivateKey) ks.getKey(keyAlias, "12345678".toCharArray());
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(pk);
        signature.update(messageBytes);
        byte[] signed = signature.sign();
        byte sign_asc[] = new byte[signed.length * 2];
        hex2Ascii(signed.length, signed, sign_asc);
        return new String(sign_asc);
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

    /**
     * 使用公钥验签
     * @param publicKey  公钥文件的字节
     * @param content 原始明文
     * @param charset 编码
     * @return 是否验签成功
     */
    public static boolean rsaCheckSign_pkcs12(byte[] publicKey, String signedStr, String content, String charset) throws UnsupportedEncodingException {
        byte[] messageBytes = content.getBytes(Charset.forName(charset));
        try {
            byte signeddata[] = new byte[signedStr.length() / 2];
            ascii2Hex(signedStr.length(),signedStr.getBytes("UTF-8"),signeddata);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(publicKey));
            PublicKey pk = certificate.getPublicKey();
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(pk);
            signature.update(messageBytes);
            System.out.println("messageBytes:" + Arrays.toString(messageBytes));
            System.out.println( Arrays.toString(signeddata));
            return signature.verify(signeddata);
        } catch (Exception e) {
            throw new IllegalArgumentException("SHA1 RSA verify sign failed : " + e.getMessage(), e);
        }
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
}
