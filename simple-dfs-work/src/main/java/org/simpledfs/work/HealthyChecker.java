package org.simpledfs.work;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.net.Client;
import org.simpledfs.core.node.NodeInfo;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.PingRequest;

import java.util.concurrent.TimeUnit;

public class HealthyChecker extends ChannelInboundHandlerAdapter {

    private static Logger LOGGER = LogManager.getLogger(HealthyChecker.class);

    private static final int DEFAULT_PING_INTERVAL = 5;

    private Client client;

    private int pingInterval;

    private NodeInfo nodeInfo;

    public HealthyChecker(NodeInfo nodeinfo, Client client, int pingInterval) {
        this.nodeInfo = nodeinfo;
        this.client = client;
        this.pingInterval = pingInterval <= 0 ? DEFAULT_PING_INTERVAL : pingInterval;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        schedulePing(ctx);
        ctx.fireChannelActive();
    }

    private void schedulePing(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            Channel channel = ctx.channel();
            if (channel.isActive()) {
                Packet packet = buildPingPacket();
                LOGGER.info("[{}] Send a Ping={}", HealthyChecker.class.getSimpleName(), packet);
                schedulePing(ctx);
            }
        }, pingInterval, TimeUnit.SECONDS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().schedule(() -> {
            LOGGER.info("[{}] Try to reconnecting...", HealthyChecker.class.getSimpleName());
            if (client.isConnectAsync()){
                client.startAsync();
            }else{
                client.start();
            }
        }, 5, TimeUnit.SECONDS);
        ctx.fireChannelInactive();
    }

    private Packet buildPingPacket(){
        Packet packet = new Packet();
        packet.setId(GlobalRequestCounter.count());
        packet.setType((byte)0x00);
        PingRequest pingRequest = new PingRequest();
        pingRequest.setNodeInfo(nodeInfo);
        packet.setRequest(pingRequest);
        return packet;
    }
}