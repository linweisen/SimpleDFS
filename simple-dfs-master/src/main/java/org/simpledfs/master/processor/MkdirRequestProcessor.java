package org.simpledfs.master.processor;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.excutor.AbstractRequestProcessor;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.MkdirRequest;
import org.simpledfs.core.req.MkdirResponse;
import org.simpledfs.core.req.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class MkdirRequestProcessor extends AbstractRequestProcessor {

    private static Logger LOGGER = LogManager.getLogger(MkdirRequestProcessor.class);

    private ChannelHandlerContext ctx;

    private MkdirRequest request;

    private IDirectory root;

    private long requestId;

    public MkdirRequestProcessor(ChannelHandlerContext ctx, Request request, IDirectory root, long requestId) {
        this.ctx = ctx;
        this.request = (MkdirRequest) request;
        this.root = root;
        this.requestId = requestId;
    }

    @Override
    public void process() {
        Packet packet = new Packet();
        packet.setId(requestId);
        packet.setType((byte)0x81);
        MkdirResponse response = new MkdirResponse();

        String name = request.getName();
        List<String> directoryLevel = getDirectoryLevel(request.getParent());
        String topParent = getTopParent(directoryLevel);
        Lock writeLock = DirectoryLock.getInstance().getLock(topParent).writeLock();
        try {
            writeLock.lock();
            StringBuilder path = new StringBuilder();
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
            IDirectory newDir = des.createChildDir(name);
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

    private List<String> getDirectoryLevel(String parent){
        List<String> directoryList = new ArrayList<>();
        directoryList.add(IDirectory.SEPARATOR);
        if (parent == null){
            return directoryList;
        }
        if (parent.length() == 1){
            return directoryList;
        }
        StringBuilder tmp = new StringBuilder(parent);
        if (!parent.endsWith(IDirectory.SEPARATOR)){
            tmp.append(IDirectory.SEPARATOR);
        }
        tmp.deleteCharAt(0);
        int index = tmp.indexOf(IDirectory.SEPARATOR);
        while (index != -1){
            directoryList.add(tmp.substring(index));
            tmp.delete(0,index);
            index = tmp.indexOf(IDirectory.SEPARATOR);
        }
        return directoryList;
    }

    private String getTopParent(List<String> directoryLevel){
        if (directoryLevel.size() == 1){
            return directoryLevel.get(0);
        }
        return directoryLevel.get(0) + directoryLevel.get(1);

    }

}
