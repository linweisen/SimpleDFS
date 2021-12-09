package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;

public abstract class AbstractRequestProcessor implements Processor {

    protected ChannelHandlerContext ctx;

    protected Request request;

    protected Context context;

    protected long packetId;

    public AbstractRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long packetId) {
        this.ctx = ctx;
        this.request = request;
        this.context = context;
        this.packetId = packetId;
    }

    public abstract void process();

    @Override
    public void run() {
        process();
    }

    protected void writeResponse(ChannelHandlerContext ctx, Packet response) {
        if (response != null) {
            ctx.channel().writeAndFlush(response);
        }
    }

    protected Packet buildResponsePacket(){
        Packet packet = new Packet();
        packet.setId(packetId);
        packet.setType((byte)(request.getType() & (byte) 0x80));
        return packet;
    }

}
