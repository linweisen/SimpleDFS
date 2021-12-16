package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.MkdirRequestProcessor;
import org.simpledfs.core.excutor.Processor;

public class MkdirRequest extends AbstractRequest {

    private String user;

    private String group;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
