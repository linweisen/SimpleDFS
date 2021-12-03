package org.simpledfs.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class DefaultClient implements Client{

    private ServerInfo serverInfo;

    private volatile boolean connected = false;

    private Channel channel = null;

    @Override
    public void connect() {
//        Assert.notNull(serverAttr, "serverAttr can not be null");
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ClientInitializer(DefaultClient.this));
                    }
                });

        ChannelFuture future = bootstrap.connect(serverInfo.getAddress(), serverInfo.getPort());
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> f) throws Exception {
                channel = future.channel();
                if (f.isSuccess()) {
                    connected = true;
//                    log.info("[{}] Has connected to {} successfully", GenericClient.class.getSimpleName(), serverAttr);
                } else {
//                    log.warn("[{}] Connect to {} failed, cause={}", GenericClient.class.getSimpleName(), serverAttr, f.cause().getMessage());
                    // fire the channelInactive and make sure
                    // the {@link HealthyChecker} will reconnect
                    channel.pipeline().fireChannelInactive();
                }
            }
        });
    }
}
