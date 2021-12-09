package org.simpledfs.core.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.simpledfs.core.excutor.Actuator;
import org.simpledfs.core.excutor.DefaultActuator;
import org.simpledfs.core.req.Request;
import org.simpledfs.core.utils.RequestResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultPacketHandler extends SimpleChannelInboundHandler<Packet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultPacketHandler.class);

    private Actuator<Packet> actuator;

    public DefaultPacketHandler() {
        actuator = new DefaultActuator<>();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        byte type = packet.getType();
        if (RequestResponseUtils.isRequest(type)) {
            onRequest(ctx, packet);
        } else {
            onResponse(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Exception occurred cause={} will close the channel:{}", cause.getMessage(), ctx.channel(), cause);
        ctx.close();
    }

    private void onRequest(ChannelHandlerContext ctx, Packet packet) {
        Request request = packet.getRequest();
//        actuator.execute(ctx, request, con);
    }


    private void onResponse(Packet packet) {
        System.out.println(packet);
    }
}
