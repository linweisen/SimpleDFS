package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.MkdirRequestProcessor;
import org.simpledfs.core.excutor.Processor;

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
