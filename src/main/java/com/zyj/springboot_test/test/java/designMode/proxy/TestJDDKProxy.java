package com.zyj.springboot_test.test.java.designMode.proxy;

import com.zyj.springboot_test.bean.HelloBody;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;

public class TestJDDKProxy {
    public static void main(String args[]) {

        HelloBody helloBody = new HelloBody();
//        Proxy.newProxyInstance(helloBody.getClass().getClassLoader(),);

    }

    private class DoInvoke implements InvokeHandler {
        @Override
        public OutputStream _invoke(String method, InputStream input, ResponseHandler handler) throws SystemException {
            return null;
        }
    }
}
