package org.simpledfs.master.processor;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.excutor.AbstractRequestProcessor;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.MkdirResponse;
import org.simpledfs.core.req.Request;
import org.simpledfs.master.MasterContext;
import org.simpledfs.master.req.MkdirRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class MkdirRequestProcessor extends AbstractRequestProcessor {

    private static Logger LOGGER = LogManager.getLogger(MkdirRequestProcessor.class);

    public MkdirRequestProcessor(ChannelHandlerContext ctx, Request request, Context context, long packetId) {
        super(ctx, request, context, packetId);
    }

    @Override
    public void process() {
        MkdirRequest mkdirRequest = (MkdirRequest)request;
        Packet packet = new Packet();
        packet.setId(packetId);
        packet.setType((byte)0x81);
        MkdirResponse response = new MkdirResponse();

        String name = mkdirRequest.getName();

        String[] directoryLevel = getDirectoryLevel(mkdirRequest.getParent());

        String topParent = getTopParent(directoryLevel);
        Lock writeLock = DirectoryLock.getInstance().getLock(topParent).writeLock();
        IDirectory root = ((MasterContext)this.context).getRoot();
        try {
            writeLock.lock();
            IDirectory newDir = null;
            StringBuilder path = new StringBuilder();
            if (directoryLevel == null){
                newDir = root.createChildDir(name);
                path.append(IDirectory.SEPARATOR + name);
            }else{
                IDirectory des = null;
                for (String d : directoryLevel){
                    des = root.findDirectory(d);
                    if (des.isDirectory()){
                        path.append(d);
                    }else{
                        LOGGER.error("directory is not exits...");
                        response.setMessage(path + "directory is not exits...");
                        break;
                    }
                }
                newDir = des.createChildDir(name);
            }
            if (newDir == null){
                response.setMessage(path + "directory make filed...");
            }else{
                response.setMessage(path + "directory make succeed...");
            }
        }finally {
            writeLock.unlock();
        }
        packet.setResponse(response);
        writeResponse(ctx, packet);
    }

    /**
     *
     * @param parent
     * @return
     */
    private String[] getDirectoryLevel(String parent){
        if (parent == null){
            return null;
        }
        StringBuilder tmp = new StringBuilder(parent);
        if (!parent.endsWith(IDirectory.SEPARATOR)){
            tmp.append(IDirectory.SEPARATOR);
        }
        return parent.split(IDirectory.SEPARATOR);
    }

    private String getTopParent(String[] directoryLevel){
        if (directoryLevel == null || directoryLevel.length == 0){
            return IDirectory.SEPARATOR;
        }

        return directoryLevel[0];
    }

}
