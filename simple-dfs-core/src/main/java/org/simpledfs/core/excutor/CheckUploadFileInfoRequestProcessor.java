package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.req.CheckUploadFileInfoRequest;
import org.simpledfs.core.req.Request;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/23
 **/
public class CheckUploadFileInfoRequestProcessor extends AbstractRequestProcessor {

    public CheckUploadFileInfoRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long packetId) {
        super(ctx, request, context, packetId);
    }

    @Override
    public void process() {
        MetaContext meta = (MetaContext) context;
        CheckUploadFileInfoRequest cRequest = (CheckUploadFileInfoRequest) request;
        String path = cRequest.getPath();

    }

    private boolean checkPathExist(String path){
        return false;
    }
}
