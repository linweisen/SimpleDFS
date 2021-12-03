package org.simpledfs.core.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;


public class PacketMessageCodec extends ByteToMessageCodec<Packet> {

//    private SerializerChooser chooser;

    public PacketMessageCodec() {
//        this.chooser = DefaultSerializerChooser.getInstance();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
