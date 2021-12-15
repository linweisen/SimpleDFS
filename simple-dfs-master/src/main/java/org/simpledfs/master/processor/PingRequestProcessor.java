package org.simpledfs.master.processor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.excutor.AbstractRequestProcessor;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.WorkInfo;
import org.simpledfs.master.req.PingRequest;
import org.simpledfs.master.req.PingResponse;

public class PingRequestProcessor extends AbstractRequestProcessor {

    public PingRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long packetId) {
        super(ctx, request, context, packetId);
    }

    @Override
    public void process() {
        PingRequest pingRequest = (PingRequest) request;
        WorkInfo workInfo = pingRequest.getWorkInfo();
        MetaContext meta = (MetaContext) context;
//        if (checkWorkInfo(workInfo)){
//            meta.addWork(workInfo);
//        }
        Packet packet = buildResponsePacket();
        PingResponse pingResponse = new PingResponse();
        packet.setResponse(pingResponse);
        writeResponse(ctx, packet);
    }
    //
    private boolean checkWorkInfo(WorkInfo workInfo){
        return true;
    }
}
