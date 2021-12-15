package org.simpledfs.work;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.net.Client;
import org.simpledfs.core.net.IdleStateChecker;
import org.simpledfs.core.node.NodeInfo;
import org.simpledfs.core.packet.PacketMessageCodec;


public class WorkClientInitializer extends ChannelInitializer<SocketChannel> {

    private Client client;

    private Configuration config;

    private NodeInfo nodeInfo;

    public WorkClientInitializer(NodeInfo nodeInfo,
                                 Client client, Configuration config) {
        this.nodeInfo = nodeInfo;
        this.client = client;
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateChecker(config.getInt(WorkConfigurationKey.IDLE_STATE, 60)));
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(new HealthyChecker(nodeInfo, client, config.getInt(WorkConfigurationKey.HEALTH_CHECKER, 30)));
        pipeline.addLast(new WorkClientHandler());
    }

}