package org.linweisen.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.linweisen.common.utils.KryoUtils;

import java.util.List;

public class ProtocolDecodeHandler extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object obj = KryoUtils.readFromByteArray(byteBuf);
        list.add(obj);

    }
}
