package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.excutor.MkdirRequestProcessor;
import org.simpledfs.core.excutor.Processor;

public class MkdirRequest extends AbstractRequest {

    public MkdirRequest(byte type) {
        super((byte)0x01);
    }


    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request){
        MkdirRequestProcessor processor = new MkdirRequestProcessor(ctx, request);
        return processor;
    }

}
