package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;

public abstract class AbstractRequestProcessor implements Processor {

    protected ChannelHandlerContext ctx;

    protected Request request;

    protected Context context;

    protected long requestId;

    public AbstractRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long requestId) {
        this.ctx = ctx;
        this.request = request;
        this.context = context;
        this.requestId = requestId;
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


}
