package org.simpledfs.master.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.Processor;
import org.simpledfs.core.req.AbstractRequest;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.processor.MkdirRequestProcessor;

public class MkdirRequest extends AbstractRequest {

    private String name;

    private String parent;

    public MkdirRequest() {
        super((byte)0x01);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        MkdirRequestProcessor processor = new MkdirRequestProcessor(ctx, request, context, packetId);
        return processor;
    }

}
