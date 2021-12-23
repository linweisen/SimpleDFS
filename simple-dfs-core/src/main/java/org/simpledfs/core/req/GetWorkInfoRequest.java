package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.GetWorkInfoRequestProcessor;
import org.simpledfs.core.excutor.Processor;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/21
 **/
public class GetWorkInfoRequest extends AbstractRequest {

    public GetWorkInfoRequest() {
        super((byte)0x03);
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        GetWorkInfoRequestProcessor processor = new GetWorkInfoRequestProcessor(ctx, request, context, packetId);
        return processor;
    }
}
