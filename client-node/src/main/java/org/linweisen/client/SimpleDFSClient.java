package org.linweisen.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.linweisen.common.protocol.FileTransferProtocol;
import org.linweisen.common.protocol.ProtocolDecodeHandler;
import org.linweisen.common.protocol.ProtocolEncodeHandler;
import org.linweisen.common.utils.FileTransferProtocolUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimpleDFSClient {

    private String host;

    private int port;



    public SimpleDFSClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void send(List<FileTransferProtocol> fpList) throws InterruptedException {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 配置启动辅助类
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolEncodeHandler());
                            ch.pipeline().addLast(new ProtocolDecodeHandler());
                            ch.pipeline().addLast(new FileSendHandler());
                        }
                    });
            // 异步连接服务器，同步等待连接成功
            ChannelFuture f = b.connect(host, port).sync();
            for (FileTransferProtocol fp : fpList){
                f.channel().writeAndFlush(fp);
            }
            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        SimpleDFSClient client = new SimpleDFSClient("127.0.0.1", 8080);
        List<FileTransferProtocol> fpList = FileTransferProtocolUtils.build(new File("/Users/linweisen/Downloads/UnityHubSetup.dmg"),
                10485760, "1");

        client.send(fpList);
    }




}
