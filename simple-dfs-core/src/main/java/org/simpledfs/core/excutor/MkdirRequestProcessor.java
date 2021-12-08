package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.req.MkdirRequest;
import org.simpledfs.core.req.Request;

public class MkdirRequestProcessor extends AbstractRequestProcessor{

    private ChannelHandlerContext ctx;

    private MkdirRequest request;

    public MkdirRequestProcessor(ChannelHandlerContext ctx, Request request) {
        this.ctx = ctx;
        this.request = (MkdirRequest) request;
    }


    @Override
    public void process() {
        String name = request.getName();

//        writeResponse(ctx);
    }
}
