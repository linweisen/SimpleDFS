package org.simpledfs.work;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkServer {

    private static Logger LOGGER = LogManager.getLogger(WorkServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void start(int port) {
        init();
        doStart(port);
    }

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

    public void init(){
        shutdownHook();
        LOGGER.info("start init...");
    }

    public void shutdownHook(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                LOGGER.info("release...");
            }
        }, "shutdownHook-thread");
        t.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(t);
    }
    

    public static void main(String[] args) {
        WorkServer workServer = new WorkServer();
        workServer.start(8080);


    }
}
