package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.CheckUploadFileInfoRequestProcessor;
import org.simpledfs.core.excutor.Processor;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/23
 **/
public class CheckUploadFileInfoRequest extends AbstractRequest {

    private String path;

    private String fileName;

    public CheckUploadFileInfoRequest() {
        super((byte)0x02);
    }

    @Override
    public Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId){
        CheckUploadFileInfoRequestProcessor processor = new CheckUploadFileInfoRequestProcessor(ctx, request, context, packetId);
        return processor;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
