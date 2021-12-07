package org.simpledfs.core.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.packet.Packet;

public class DefaultServer implements Server {

    private static Logger LOGGER = LogManager.getLogger(DefaultServer.class);

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private int port;

    private ChannelInitializer<SocketChannel> initializer;

    private Class clazz;

    public DefaultServer(Class clazz, ChannelInitializer<SocketChannel> initializer, int port) {
        this.port = port;
        this.initializer = initializer;
        this.clazz = clazz;
    }

    public void init(){

    }

    @Override
    public void start(){
        init();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        StopWatch watch = new StopWatch();
        watch.start();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(initializer);

        ChannelFuture future = bootstrap.bind(port);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    watch.stop();
                    long cost = watch.getTime();
                    LOGGER.info("[{}] Startup at port:{} cost:{}[ms]", clazz.getSimpleName(), port, cost);

                }
            }
        });
    }

}
