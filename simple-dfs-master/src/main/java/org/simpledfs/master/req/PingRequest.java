package org.simpledfs.master.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.Processor;
import org.simpledfs.core.req.AbstractRequest;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.WorkInfo;
import org.simpledfs.master.processor.PingRequestProcessor;

public class PingRequest extends AbstractRequest {

    private WorkInfo workInfo;

    public PingRequest() {
        super((byte)0x00);
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        PingRequestProcessor processor = new PingRequestProcessor(ctx, request, context, packetId);
        return processor;
    }

    public WorkInfo getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(WorkInfo workInfo) {
        this.workInfo = workInfo;
    }
}
