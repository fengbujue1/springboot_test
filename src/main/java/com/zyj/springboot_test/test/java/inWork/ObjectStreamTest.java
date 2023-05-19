package com.zyj.springboot_test.test.java.inWork;

import com.zyj.springboot_test.test.java.inWork.model.ICBC_BXNR_REQ;

import java.io.*;

/**
 * @Author 周赟吉
 * @Date 2023/5/17 15:55
 * @Description :
 */
public class ObjectStreamTest {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        ICBC_BXNR_REQ req = new ICBC_BXNR_REQ();
        req.name = "asd";
        outputStream.writeObject(req);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        ICBC_BXNR_REQ out = (ICBC_BXNR_REQ)objectInputStream.readObject();
        System.out.println(out.getBTX());
        System.out.println(out.name);
    }
}
