package com.zyj.springboot_test.test.java.netWork.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server_Netty {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(3);

        serverBootstrap
                .group(bossGroup, workerGroup)
                .handler(new ChannelHandler() {
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("handlerAdded");
                    }

                    @Override
                    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("handlerRemoved");
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        System.out.println("exceptionCaught");
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        System.out.println("chanelPipeline" + p);
                        p.addLast(new NettyServerHandler());
                    }
                })
                .channel(NioServerSocketChannel.class);

        ChannelFuture sync = serverBootstrap.bind(9000).sync();
        System.out.println("start");

        sync.channel().closeFuture().sync();//主线程在这里阻塞等待 结束
        System.out.println("end");
    }
}
