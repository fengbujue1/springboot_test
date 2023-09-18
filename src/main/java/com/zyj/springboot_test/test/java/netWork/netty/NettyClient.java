package com.zyj.springboot_test.test.java.netWork.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @Author 周赟吉
 * @Date 2023/7/31 14:40
 * @Description :
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        //客户端线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(group)
                    // 使用NioSocketChannel作为客户端的通道实现
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //加入处理器
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });

            System.out.println("Client start");
            //启动客户端去连接服务器端
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 8088).sync();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("输入数据：");
                String comad = scanner.nextLine();
                if ("quit".equals(comad)) {
                    break;
                }
                ByteBuf buf = Unpooled.copiedBuffer(comad.getBytes(CharsetUtil.UTF_8));
                cf.channel().writeAndFlush(buf);
            }
            cf.channel().close();
            //对通道关闭进行监听
            cf.channel().closeFuture().sync();
            System.out.println("Client close");
        } finally {
            group.shutdownGracefully();
        }
    }
}
