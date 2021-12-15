package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.ReadDirectoryRequest;
import org.simpledfs.core.req.Request;

import java.util.concurrent.locks.Lock;

public class ReadDirectoryRequestProcessor extends AbstractRequestProcessor {

    public ReadDirectoryRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long requestId) {
        super(ctx, request, context, requestId);
    }

    @Override
    public void process() {
        Packet packet = buildResponsePacket();

        ReadDirectoryRequest readDirectoryRequest = (ReadDirectoryRequest) request;
        String directoryName = readDirectoryRequest.getDirectory();
        String[] paths = splitPath(directoryName);
        String topParent = null;
        if (paths.length == 0){
            topParent = IDirectory.SEPARATOR;
        }else{
            topParent = paths[0];
        }
        Lock readLock = DirectoryLock.getInstance().getLock(topParent).readLock();
        try {
            readLock.lock();

        }finally {
            readLock.unlock();
        }
        writeResponse(ctx, packet);
    }

    private String[] splitPath(String directory){
        return directory.split(IDirectory.SEPARATOR);
    }

}
