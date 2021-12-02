package org.linweisen.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.linweisen.common.utils.KryoUtils;

public class ProtocolEncodeHandler extends MessageToByteEncoder<AbstractProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractProtocol abstractProtocol, ByteBuf byteBuf) throws Exception {
        byte[] b = KryoUtils.writeToByteArray(abstractProtocol);
        byteBuf.writeBytes(b);
        ctx.flush();
    }
}
