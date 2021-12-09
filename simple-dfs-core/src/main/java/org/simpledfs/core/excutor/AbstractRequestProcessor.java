package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.packet.Packet;

public abstract class AbstractRequestProcessor implements Processor {

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
}
