package org.simpledfs.core.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.packet.Packet;

public interface Client {

    public void start();

    public void send(Packet packet);

    public void setInitializer(ChannelInitializer<SocketChannel> channelInitializer);
}
