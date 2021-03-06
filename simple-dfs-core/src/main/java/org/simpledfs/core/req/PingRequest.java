package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.PingRequestProcessor;
import org.simpledfs.core.excutor.Processor;
import org.simpledfs.core.node.NodeInfo;

public class PingRequest extends AbstractRequest {

    private NodeInfo nodeInfo;

    public PingRequest() {
        super((byte)0x00);
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        PingRequestProcessor processor = new PingRequestProcessor(ctx, request, context, packetId);
        return processor;
    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }
}
