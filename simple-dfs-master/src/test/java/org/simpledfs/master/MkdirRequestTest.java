package org.simpledfs.master;

import io.netty.channel.ChannelHandlerContext;
import org.junit.Test;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.dir.Directory;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.req.MkdirRequest;

public class MkdirRequestTest {

    private ChannelHandlerContext ctx;

    private MkdirRequest request;

    private MetaContext context;

    private long requestId;

    @Test
    public void mkdirTest(){
        IDirectory root = buildDirectory();
        DirectoryLock.getInstance().addLock(root.getName());
        ctx = null;
//        context = new MasterContext(root);
//        requestId = 1L;
//        MkdirRequest request = new MkdirRequest();
//        request.setName("a");
//        MkdirRequestProcessor processor = new MkdirRequestProcessor(ctx, request, context, requestId);
//        processor.process();
    }

    private IDirectory buildDirectory(){
        IDirectory root = new Directory("/", "-1");
        return root;
    }
}
