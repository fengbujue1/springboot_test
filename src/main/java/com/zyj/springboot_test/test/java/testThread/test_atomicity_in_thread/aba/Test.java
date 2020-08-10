package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread.aba;

import java.util.concurrent.locks.LockSupport;

/**
 * 演示ABA的问题。<br/>
 * 栈原本是A/B
 *
 */
public class Test {
	
    public static void main(String[] args) throws InterruptedException {
        Stack stack = new Stack();

        // 栈原本是A/B
        stack.push(new Node("B"));      //B入栈
        stack.push(new Node("A"));      //A入栈
        
        // 线程0，弹出A，并通过CAS(A,B)，将B设为栈顶
        Thread thread1 = new Thread(() -> {
            Node node = stack.pop(800);
            System.out.println(Thread.currentThread().getName() +" "+ node.toString());

            System.out.println("done...");
        });
        thread1.start();
        
        // 线程0 CAS操作完成前，线程1动作比较快
        Thread thread2 = new Thread(() -> {
            LockSupport.parkNanos(1000 * 1000 * 300L);
            
            // 线程1将A/B都取出来，此时栈是空的
            Node nodeA = stack.pop(0);      //取出A
            System.out.println(Thread.currentThread().getName()  +" "+  nodeA.toString());
            Node nodeB = stack.pop(0);      //取出B，之后B处于游离状态
            System.out.println(Thread.currentThread().getName()  +" "+  nodeB.toString());
            
            // 然后压入3个元素，栈变为：A/C/D
            stack.push(new Node("D"));      //D入栈
            stack.push(new Node("C"));      //C入栈
            stack.push(nodeA);                    //A入栈

            System.out.println("done...");
        });
        thread2.start();
        
        // 线程0继续执行，发现A还在，CAS(A,B)操作成功，其实B已经被线程1弹出了，处于游离状态
        LockSupport.parkNanos(1000 * 1000 * 1000 * 2L);
        
        // 取出A后，正确的结果应该C/D，结果是B
        System.out.println("开始遍历Stack：");
        Node node = null;
        while ((node = stack.pop(0))!=null){
            System.out.println(node.value);
        }
    }
}
