package org.simpledfs.master.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.Processor;
import org.simpledfs.core.req.AbstractRequest;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.processor.MkdirRequestProcessor;

public class ReadDirectoryRequest extends AbstractRequest {

    private String directory;

    public ReadDirectoryRequest() {
        super((byte)0x02);
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        MkdirRequestProcessor processor = new MkdirRequestProcessor(ctx, request, context, packetId);
        return processor;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
