package org.simpledfs.master;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.excutor.Actuator;
import org.simpledfs.core.excutor.DefaultActuator;
import org.simpledfs.core.packet.DefaultPacketHandler;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;
import org.simpledfs.core.utils.RequestResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultMasterPacketHandler extends SimpleChannelInboundHandler<Packet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultMasterPacketHandler.class);

    private Actuator actuator;

    private MetaContext meta;

    public DefaultMasterPacketHandler(MetaContext meta) {
        this.actuator = DefaultActuator.getInstance();
        this.meta = meta;
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
        actuator.execute(ctx, request, meta, packet.getId());
    }

    private void onResponse(Packet packet) {
        System.out.println(packet);
    }

}
