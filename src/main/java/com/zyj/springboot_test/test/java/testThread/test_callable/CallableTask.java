package com.zyj.springboot_test.test.java.testThread.test_callable;

import java.util.concurrent.Callable;

public class CallableTask<V> implements Callable {
    @Override
    public String call() throws Exception {
        Thread.sleep(3000);

        return "result";
    }
}
