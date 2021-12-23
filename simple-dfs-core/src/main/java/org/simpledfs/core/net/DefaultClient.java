package org.simpledfs.core.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.packet.PendingPackets;
import org.simpledfs.core.req.Request;
import org.simpledfs.core.req.Response;
import org.simpledfs.core.serialize.SerializeType;
import org.simpledfs.core.serialize.SerializerChooser;
import org.simpledfs.core.uuid.SnowflakeGenerator;
import org.simpledfs.core.uuid.UUIDGenerator;

import java.util.concurrent.CompletableFuture;

public class DefaultClient implements Client {

    private static Logger LOGGER = LogManager.getLogger(DefaultClient.class);

    private ChannelInitializer<SocketChannel> channelInitializer;

    private ServerInfo serverInfo;

    private Channel channel = null;

    private boolean connected = false;

    private Object connectMonitor = new Object();

    private EventLoopGroup group;

    private volatile int retry = 0;

    private boolean connectAsync;

    private UUIDGenerator uuid = new SnowflakeGenerator(0, 0);

    public DefaultClient(ServerInfo serverInfo, boolean connectAsync) {
        this.serverInfo = serverInfo;
        this.connectAsync = connectAsync;
    }


    @Override
    public void startAsync() {
        Bootstrap bootstrap = connect();
        ChannelFuture future = bootstrap.connect(serverInfo.getAddress(), serverInfo.getPort());
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> f) throws Exception {
                channel = future.channel();
                if (f.isSuccess()) {
                    connected = true;
                    synchronized (connectMonitor){
                        connectMonitor.notify();
                    }
                    LOGGER.info("[{}] Has connected to {} successfully", DefaultClient.class.getSimpleName(), serverInfo);
                } else {
                    LOGGER.warn("[{}] Connect to {} failed, cause={}", DefaultClient.class.getSimpleName(), serverInfo, f.cause().getMessage());
                    /*
                     * fire the channelInactive and make sure
                     * the {@link HealthyChecker} will reconnect
                     */
                    if (retry < 1){
                        channel.pipeline().fireChannelInactive();
                    }else{
                        channel.closeFuture().addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                group.shutdownGracefully().addListener( f -> {
                                    System.exit(0);
                                });
                            }
                        });
                    }
                    retry += 1;
                }
            }
        });
    }

    @Override
    public void start() {
        Bootstrap bootstrap = connect();
        try {
            ChannelFuture future = bootstrap.connect(serverInfo.getAddress(), serverInfo.getPort()).sync();
            channel = future.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.warn("[{}] Connect to {} failed, cause={}", DefaultClient.class.getSimpleName(), serverInfo, e.getMessage());
            group.shutdownGracefully();
        } catch (Exception e){
            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    @Override
    public Response send(Request request) {
        CompletableFuture<Packet> promise = new CompletableFuture<>();
        Packet packet = new Packet();
        packet.setRequest(request);
        packet.setType(request.getType());
        Long id = uuid.getUID();
        PendingPackets.add(id, promise);
        packet.setId(id);
        packet.setSerialize(SerializeType.Kryo.getType());
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
        Packet resPacket = promise.join();
        return resPacket.getResponse();
    }

    private Bootstrap connect(){
        this.group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(channelInitializer);
                    }
                });
        return bootstrap;
    }

    public Object getStartMonitor() {
        return connectMonitor;
    }


    @Override
    public void setInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    @Override
    public boolean isConnectAsync() {
        return connectAsync;
    }

    public ChannelInitializer<SocketChannel> getChannelInitializer() {
        return channelInitializer;
    }

}
