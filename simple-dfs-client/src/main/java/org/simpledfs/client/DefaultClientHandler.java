package org.simpledfs.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultClientHandler extends SimpleChannelInboundHandler<Packet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultClientHandler.class);

    private Response response;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        LOGGER.debug("ClientPacketDispatcher has received {}", packet);
        byte type = packet.getType();
        response = packet.getResponse();
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
