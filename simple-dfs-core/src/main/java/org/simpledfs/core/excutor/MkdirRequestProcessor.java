package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.dir.INode;
import org.simpledfs.core.dir.Snapshot;
import org.simpledfs.core.exception.directory.DirectoryAlreadyExistException;
import org.simpledfs.core.exception.directory.DirectoryNameIllegalException;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.MkdirRequest;
import org.simpledfs.core.req.MkdirResponse;
import org.simpledfs.core.req.Request;
import org.simpledfs.core.utils.MD5Utils;

import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Lock writeLock = null;
        try {

            //1、去掉文件夹名称前后字符'/';2、检测是否有特殊字符
            String name = checkDirectoryName(mkdirRequest.getName());

            //将parent directory 按 / 分割
            String[] directoryLevel = getDirectoryLevel(mkdirRequest.getParent());

            //获取非除了根目录之后顶层的directory
            String topParent = getTopParent(directoryLevel);

            //加锁
            writeLock = DirectoryLock.getInstance().getLock(topParent).writeLock();
            MetaContext meta = (MetaContext)this.context;
            IDirectory root = meta.getRoot();
            writeLock.lock();

            IDirectory newDir = null;
            StringBuilder path = new StringBuilder(IDirectory.SEPARATOR);
            if (directoryLevel == null || directoryLevel.length == 0){

                //检测文件夹是否已存在
                checkDirectoryExist(root, name);
                newDir = root.createChildDir(name);
                DirectoryLock.getInstance().addLock(name);
                path.append(name);
            }else{
                IDirectory des = root;
                for (String d : directoryLevel){
                    des = des.findDirectory(d);
                    if (des.isDirectory()){
                        path.append(d + IDirectory.SEPARATOR);
                    }else{
                        LOGGER.error("{} directory is not exits...", path);
                        response.setMessage(path + "directory is not exits...");
                        break;
                    }
                }
                newDir = des.createChildDir(name);
                des.addChildDirectory(newDir);

            }
            path.append(name);
            String id = MD5Utils.getMD5String(path.toString());
            newDir.setId(id);
            INode inode = INode.build(mkdirRequest.getUser(), mkdirRequest.getGroup(),
                    new char[]{'d', 'r', 'w', 'x', 'r', 'w', 'x', '-', '-', '-'});
            newDir.setINode(inode);
            Snapshot snapshot = meta.getSnapshot();
            snapshot.write(newDir);
            LOGGER.info("directory {} create succeed...", name);
            response.setMessage(path + "directory make succeed...");
        }catch (DirectoryNameIllegalException | DirectoryAlreadyExistException e){
            LOGGER.error("directory create failed caused by {}", e.getMessage());
            response.setMessage(e.getMessage());
        } finally {
            if (writeLock != null){
                writeLock.unlock();
            }
        }

        packet.setResponse(response);
        writeResponse(ctx, packet);
    }


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

    private String checkDirectoryName(String name) {
        StringBuilder nameBuilder = new StringBuilder(name);
        if (name.startsWith(IDirectory.SEPARATOR)){
            nameBuilder.deleteCharAt(0);
        }
        if (name.endsWith(IDirectory.SEPARATOR)){
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
        }
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(nameBuilder.toString());
        if (m.find()){
            throw new DirectoryNameIllegalException(String.format("the %s contains illegal characters", nameBuilder.toString()));
        }
        return nameBuilder.toString();
    }

    private boolean checkDirectoryExist(IDirectory d, String name){
        if (d.findDirectory(name) != null){
            String message = "the %s had exist";
            throw new DirectoryAlreadyExistException(String.format(message, name));
        }else{
            return false;
        }
    }
}
