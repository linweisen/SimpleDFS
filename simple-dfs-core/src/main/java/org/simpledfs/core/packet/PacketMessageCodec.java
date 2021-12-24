package org.simpledfs.core.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.simpledfs.core.serialize.Serializer;
import org.simpledfs.core.serialize.SerializerChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class PacketMessageCodec extends ByteToMessageCodec<Packet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(PacketMessageCodec.class);

    private SerializerChooser chooser;

    public PacketMessageCodec() {
        this.chooser = SerializerChooser.getInstance();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        // check the packet
        if (!checkPacket(packet)) {
            throw new RuntimeException("checkPacket failed!");
        }
        byte serialize = packet.getSerialize();
        Serializer serializer = chooser.choose(serialize);
        // get packet content bytes
        byte[] content = serializer.serialize(packet);
        // do encode
        byteBuf.writeByte(packet.getMAGIC());
        byteBuf.writeByte(packet.getType());
        byteBuf.writeLong(packet.getId());
        byteBuf.writeByte(serialize);
        byteBuf.writeInt(content.length);
        byteBuf.writeBytes(content);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // len = 1byte(magic) + 1byte(type) + 8byte(id) + 1type(serialize) + 4bytes(content length)
        int leastPacketLen = 15;
        // until we can read at least 7 bytes
        if (byteBuf.readableBytes() < leastPacketLen) {
            return;
        }
        // mark reader index at here
        // if no enough bytes arrived
        // we should wait and reset the
        // reader index to here
        byteBuf.markReaderIndex();
        // do common check before decode
        byte magic = byteBuf.readByte();
        byte type = byteBuf.readByte();
        long id = byteBuf.readLong();

        byte serialize = byteBuf.readByte();
        Serializer serializer = chooser.choose(serialize);
//        Assert.notNull(serializer, "No serializer is chosen cause the algorithm of packet is invalid");

        int len = byteBuf.readInt();
        // until we have the entire packet received
        if (byteBuf.readableBytes() < len) {
            // after read some bytes: magic/algorithm/type/len
            // the left readable bytes length is less than len
            // so we need to wait until enough bytes received
            // but we must reset the reader index to we marked
            // before we return
            byteBuf.resetReaderIndex();
            return;
        }
        // read content
        byte[] content = new byte[len];
        byteBuf.readBytes(content);

        Packet packet = serializer.deserialize(content, Packet.class);
        list.add(packet);
    }

    private boolean checkPacket(Packet packet) {
        byte magic = packet.getMAGIC();
        if (magic != Packet.MAGIC_CODE){
            LOGGER.error("magic={} is invalid with packet={}", magic, packet);
            return false;
        }

        long id = packet.getId();
        if (id == 0){
            LOGGER.error("id={} is invalid with packet={}", id, packet);
            return false;
        }

        byte serialize = packet.getSerialize();
        Serializer serializer = chooser.choose(serialize);
        if (serializer == null) {
            LOGGER.error("serialize={} is invalid with packet={}", serialize, packet);
            return false;
        }

        return true;
    }
}
