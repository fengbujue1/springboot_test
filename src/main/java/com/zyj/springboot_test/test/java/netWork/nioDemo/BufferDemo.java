package com.zyj.springboot_test.test.java.netWork.nioDemo;

import java.nio.ByteBuffer;

/**
 * 内部是一个带有两个索引下标的数组：position和limit。capacity是不变的。
 *
 */
public class BufferDemo {
    public static void main(String[] args) {
        // 构建一个byte字节缓冲区，容量是4
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 默认写入模式，查看三个重要的指标
        System.out.println(String.format("初始化：capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
                byteBuffer.position(), byteBuffer.limit()));
        // 写入2字节的数据
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        // 再看三个指针的位置
        System.out.println(String.format("写入3字节后，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
                byteBuffer.position(), byteBuffer.limit()));

        // 转换为读取模式(不调用flip方法，也是可以读取数据的，但是position记录读取的位置不对)
        System.out.println("#######开始读取");
        byteBuffer.flip();	// limit = position; position = 0;
        byte a = byteBuffer.get();
        System.out.println(a);
        byte b = byteBuffer.get();
        System.out.println(b);
        System.out.println(String.format("读取2字节数据后，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
                byteBuffer.position(), byteBuffer.limit()));

        // 继续写入3字节，此时读模式下，limit=3，position=2.继续写入只能覆盖写入一条数据
        // clear()方法清除整个缓冲区。compact()方法仅清除已阅读的数据。转为写入模式
        byteBuffer.compact(); // 未读的数据挪到空间最左侧；position = limit - positon; limit = capacity;
        System.out.println(byteBuffer);
        byteBuffer.put((byte) 3);
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        System.out.println(String.format("最终的情况，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
                byteBuffer.position(), byteBuffer.limit()));
        
        
        //byteBuffer.mark(); // 标记当前position的位置
        //byteBuffer.reset(); // 重置position为上次mark()标记的位置
        
        // 可用来重读Buffer中的所有数据。
        //byteBuffer.rewind(); // 重置position为0，mark为-1；
        
        // 清空buffer，未读取的数据无法再次读取了。
        //byteBuffer.clear();		// 基于rewind的基础，limit设置为capacity

    }
}
