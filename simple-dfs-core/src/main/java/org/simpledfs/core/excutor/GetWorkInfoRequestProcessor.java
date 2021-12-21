package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
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

    }
}
