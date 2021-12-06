package org.simpledfs.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.packet.PacketMessageCodec;


/**
 * @author houyi
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private Client client;

    public ClientInitializer(Client client) {
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
//        pipeline.addLast(new IdleStateChecker(baseConfig.readerIdleTime()));
        pipeline.addLast(new PacketMessageCodec());
//        pipeline.addLast(new HealthyChecker(client, baseConfig.pingInterval()));
        pipeline.addLast(new DefaultClientHandler());
    }

}