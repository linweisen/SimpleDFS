package org.simpledfs.work;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(WorkServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

//    private ChannelListener channelListener;

    public void doStart(int port) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        long start = System.currentTimeMillis();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WorkServerInitializer());

        ChannelFuture future = bootstrap.bind(port);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    long cost = System.currentTimeMillis() - start;
                    LOGGER.info("[{}] Startup at port:{} cost:{}[ms]", WorkServer.class.getSimpleName(), port, cost);
                    // register to router after startup successfully

                }
            }
        });
    }

    public static void main(String[] args) {
//        ServerStartupParameter param = new ServerStartupParameter();
//        JCommander.newBuilder()
//                .addObject(param)
//                .build()
//                .parse(args);
//        ServerMode serverMode = ServerMode.getEnum(param.mode);
//        RouterServerAttr routerServerAttr = RouterServerAttr.builder()
//                .address(param.routerAddress)
//                .port(param.routerPort)
//                .build();
//        Integer serverPort = param.serverPort;
//
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.serverMode(serverMode)
//                .routerServerAttr(routerServerAttr)
//                .start(serverPort);
    }
}
