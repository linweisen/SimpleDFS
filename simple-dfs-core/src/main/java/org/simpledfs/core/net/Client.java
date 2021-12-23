package org.simpledfs.core.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;
import org.simpledfs.core.req.Response;

public interface Client {

    public void startAsync();

    public void start();

    public Response send(Request request);

    public void setInitializer(ChannelInitializer<SocketChannel> channelInitializer);

    public boolean isConnectAsync();
}
