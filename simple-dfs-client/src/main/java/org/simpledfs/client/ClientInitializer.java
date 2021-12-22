package org.simpledfs.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.net.DefaultClient;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.packet.PacketMessageCodec;
import org.simpledfs.core.req.Response;


/**
 * @author linweisen
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private DefaultClientHandler handler;

    public ClientInitializer() {
        this.handler = new DefaultClientHandler();
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(handler);
    }

    public Response getResponse(){
        return handler.getResponse();
    }

}