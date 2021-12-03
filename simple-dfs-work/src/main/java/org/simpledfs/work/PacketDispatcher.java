package org.simpledfs.work;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDispatcher extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        int readerIndex = in.readerIndex();
        byte magic = in.getByte(readerIndex);

        in.clear();
        ctx.close();
    }
}
