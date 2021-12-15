package org.simpledfs.master;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.context.MetaContext;

public class MasterServerInitializer extends ChannelInitializer<SocketChannel> {

    private MetaContext meta;

    public MasterServerInitializer(MetaContext meta) {
        this.meta = meta;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new PacketDispatcher(meta));
    }
}
