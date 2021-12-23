package org.simpledfs.core.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;

public interface Client {

    public void startAsync();

    public void start();

    public void send(Request request);

    public void setInitializer(ChannelInitializer<SocketChannel> channelInitializer);

    public boolean isConnectAsync();
}
