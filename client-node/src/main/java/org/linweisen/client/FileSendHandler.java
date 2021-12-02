package org.linweisen.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.linweisen.common.protocol.AbstractProtocol;


public class FileSendHandler extends SimpleChannelInboundHandler<AbstractProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractProtocol protocol) throws Exception {

//        channelHandlerContext.writeAndFlush(protocol);
    }


}
