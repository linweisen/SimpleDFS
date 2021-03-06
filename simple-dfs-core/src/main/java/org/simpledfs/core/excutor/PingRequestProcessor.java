package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.node.NodeInfo;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.PingRequest;
import org.simpledfs.core.req.PingResponse;
import org.simpledfs.core.req.Request;

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
