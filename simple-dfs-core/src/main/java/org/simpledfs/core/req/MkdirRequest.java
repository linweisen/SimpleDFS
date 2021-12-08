package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.excutor.MkdirRequestProcessor;
import org.simpledfs.core.excutor.Processor;

public class MkdirRequest extends AbstractRequest {

    private String name;

    private String parent;

    public MkdirRequest(byte type) {
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
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request){
        MkdirRequestProcessor processor = new MkdirRequestProcessor(ctx, request);
        return processor;
    }

}
