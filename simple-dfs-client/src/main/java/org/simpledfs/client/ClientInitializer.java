package org.simpledfs.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.packet.PacketMessageCodec;


/**
 * @author linweisen
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {


    public ClientInitializer() {

    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(new DefaultClientHandler());
    }

}