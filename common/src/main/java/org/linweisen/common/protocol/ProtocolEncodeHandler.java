package org.linweisen.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncodeHandler extends MessageToByteEncoder<AbstractProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractProtocol abstractProtocol, ByteBuf byteBuf) throws Exception {

    }
}
