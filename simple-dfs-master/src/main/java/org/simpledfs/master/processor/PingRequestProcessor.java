package org.simpledfs.master.processor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.excutor.AbstractRequestProcessor;
import org.simpledfs.core.node.NodeInfo;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.req.PingRequest;
import org.simpledfs.master.req.PingResponse;

public class PingRequestProcessor extends AbstractRequestProcessor {

    public PingRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long packetId) {
        super(ctx, request, context, packetId);
    }

    @Override
    public void process() {
        PingRequest pingRequest = (PingRequest) request;
        NodeInfo nodeInfo = pingRequest.getNodeInfo();
        MetaContext meta = (MetaContext) context;
        if (checkNodeInfo(nodeInfo)){
            meta.addNode(nodeInfo);
        }
        Packet packet = buildResponsePacket();
        PingResponse pingResponse = new PingResponse();
        packet.setResponse(pingResponse);
        writeResponse(ctx, packet);
    }

    private boolean checkNodeInfo(NodeInfo nodeInfo){
        return true;
    }
}
