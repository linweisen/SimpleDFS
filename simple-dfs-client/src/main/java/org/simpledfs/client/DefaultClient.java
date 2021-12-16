package org.simpledfs.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.packet.PendingPackets;
import org.simpledfs.core.req.MkdirRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;


public class DefaultClient implements Client {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultClientHandler.class);

    private ServerInfo serverInfo;

    private volatile boolean connected = false;

    private Channel channel = null;

    public DefaultClient(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        LOGGER.info("初始化...");
    }

    @Override
    public void connect() {
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
                    LOGGER.info("[{}] Has connected to {} successfully", DefaultClient.class.getSimpleName(), serverInfo);
                } else {
//                    log.warn("[{}] Connect to {} failed, cause={}", GenericClient.class.getSimpleName(), serverAttr, f.cause().getMessage());
                    // fire the channelInactive and make sure
                    // the {@link HealthyChecker} will reconnect
                    channel.pipeline().fireChannelInactive();
                }
            }
        });
    }

    @Override
    public CompletableFuture<Packet> sendPacket(Packet packet) {
        // create a promise
        CompletableFuture<Packet> promise = new CompletableFuture<>();
        if (!connected) {
            String msg = "Not connected yet!";
            LOGGER.debug(msg);
            return promise;
        }
        Long id = packet.getId();
        PendingPackets.add(id, promise);
        ChannelFuture future = channel.writeAndFlush(packet);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> f) throws Exception {
                if (!f.isSuccess()) {
                    CompletableFuture<Packet> pending = PendingPackets.remove(id);
                    if (pending != null) {
                        pending.completeExceptionally(f.cause());
                    }
                }
            }
        });
        return promise;
    }

    public static void main(String[] args) throws InterruptedException {
        ServerInfo serverInfo = new ServerInfo("127.0.0.1", 8080);
        DefaultClient client = new DefaultClient(serverInfo);
        client.connect();
        Packet packet = new Packet();
        packet.setId(1L);
        packet.setType((byte)0x01);
        packet.setSerialize((byte)1);
        MkdirRequest request = new MkdirRequest();
        request.setName("file");
        request.setParent("/");
        packet.setRequest(request);
        while (!client.connected){
            Thread.sleep(1000);
        }
        client.sendPacket(packet);

    }
}
