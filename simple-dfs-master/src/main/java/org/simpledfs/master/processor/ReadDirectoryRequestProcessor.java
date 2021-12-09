package org.simpledfs.master.processor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.excutor.AbstractRequestProcessor;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.req.ReadDirectoryRequest;

import java.util.concurrent.locks.Lock;

public class ReadDirectoryRequestProcessor extends AbstractRequestProcessor {

    public ReadDirectoryRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long requestId) {
        super(ctx, request, context, requestId);
    }

    @Override
    public void process() {
        Packet packet = new Packet();
        packet.setId(requestId);
        packet.setType((byte)0x81);
        ReadDirectoryRequest readDirectoryRequest = (ReadDirectoryRequest) request;
        String directoryName = readDirectoryRequest.getDirectory();

        String topParent = null;
        Lock writeLock = DirectoryLock.getInstance().getLock(topParent).writeLock();
        try {

        }finally {
            writeLock.unlock();
        }
        writeResponse(ctx, packet);
    }
}
