package org.simpledfs.work;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.net.Client;
import org.simpledfs.core.net.IdleStateChecker;
import org.simpledfs.core.packet.PacketMessageCodec;


/**
 * @author houyi
 */
public class WorkClientInitializer extends ChannelInitializer<SocketChannel> {

    private Client client;

    private Configuration config;

    public WorkClientInitializer(Client client, Configuration config) {
        this.client = client;
        this.config = config;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateChecker(config.getInt("idle.state", 60)));
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(new HealthyChecker(client, config.getInt("health.checker", 30)));
        pipeline.addLast(new WorkClientHandler());
    }

}