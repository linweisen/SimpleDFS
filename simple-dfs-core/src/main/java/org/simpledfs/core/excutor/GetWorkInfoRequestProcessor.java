package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.GetWorkInfoResponse;
import org.simpledfs.core.req.Request;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/21
 **/
public class GetWorkInfoRequestProcessor extends AbstractRequestProcessor {

    public GetWorkInfoRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long packetId) {
        super(ctx, request, context, packetId);
    }

    @Override
    public void process() {
        MetaContext meta = (MetaContext) context;
        Packet packet = buildResponsePacket();
        GetWorkInfoResponse response = new GetWorkInfoResponse();
        packet.setResponse(response);
        if (meta.getNodeMap() == null){
            response.setMessage("no work...");
        }else{
//            meta.getNodeMap().entrySet().;
        }
        writeResponse(ctx, packet);
    }
}
