package org.simpledfs.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.packet.PendingPackets;
import org.simpledfs.core.req.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class DefaultClientHandler extends SimpleChannelInboundHandler<Packet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultClientHandler.class);

    private Response response;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        LOGGER.debug("ClientPacketDispatcher has received {}", packet);
//        byte type = packet.getType();
//        response = packet.getResponse();
        CompletableFuture<Packet> pending = PendingPackets.remove(packet.getId());
        if (pending != null) {
            pending.complete(packet);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        LOGGER.error("ctx close,cause:", cause);
    }

    public Response getResponse() {
        return response;
    }
}
