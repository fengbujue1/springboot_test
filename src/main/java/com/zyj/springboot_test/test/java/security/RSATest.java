package com.zyj.springboot_test.test.java.security;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSATest {
    public static void main(String[] args) throws Exception {
        String text = "o5HiI4rFSn6olrZfCfHCSr-dgmDE";
        //1.生成公私钥匙
        //KeyPairGenerator 类用于生成公钥和私钥对。密钥对生成器是使用 getInstance 工厂方法（返回一个给定类的实例的静态方法）构造的。
        //特定算法的密钥对生成器可以创建能够与此算法一起使用的公钥/私钥对。它还可以将特定于算法的参数与每个生成的密钥关联。
        //initialize(int keysize) 初始化确定密钥大小的密钥对生成器，使用默认的参数集合，
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey rsaprivateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey rsapublicKey = (RSAPublicKey) keyPair.getPublic();
        System.out.println("私钥匙:" + Base64Utils.encodeToString(rsaprivateKey.getEncoded()));
        System.out.println("公钥:" + Base64Utils.encodeToString(rsapublicKey.getEncoded()));

        //2.私钥加密,
        //PKCS8EncodedKeySpec 进行编码的专用密钥的 ASN.1 编码
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaprivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        //Cipher.ENCRYPT_MODE, 用于将 Cipher 初始化为加密模式的常量。
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());

        String s = Base64Utils.encodeToString(result);
        System.out.println("私钥加密:" + s);

        //3.公钥解密+
        // X509EncodedKeySpec 进行编码的公用密钥的 ASN.1 编码
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsapublicKey.getEncoded());
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //Cipher.DECRYPT_MODE 用于将 Cipher 初始化为解密模式的常量。
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        result = cipher.doFinal(Base64Utils.decodeFromString(s));
        System.out.println("公钥解密:" + new String(result));


        //TODO 使用SHA256加密解密
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(text.getBytes());
        result = signature.sign();
        System.out.println("SHA256withRSA加密:" + Base64Utils.encodeToString(result));

        signature.initVerify(publicKey);
        signature.update(text.getBytes());
        boolean flag = signature.verify(result);
        System.out.println("SHA256withRSA解密:" + flag);
    }
}
