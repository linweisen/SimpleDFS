package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.Processor;

public abstract class AbstractRequest implements Request {

    private byte type;

    public AbstractRequest(byte type) {
        this.type = type;
    }

    @Override
    public byte getType() {
        return type;
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        return null;
    }
}
