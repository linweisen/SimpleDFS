package org.simpledfs.master;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.simpledfs.core.net.IdleStateChecker;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.packet.PacketMessageCodec;
import org.simpledfs.master.http.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PacketDispatcher extends ByteToMessageDecoder {

    private final static Logger LOGGER = LoggerFactory.getLogger(PacketDispatcher.class);

    private MasterContext context;

    public PacketDispatcher(MasterContext context) {
        this.context = context;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        if (in.readableBytes() < 5) {
            return;
        }
        int readerIndex = in.readerIndex();
        final byte magic = in.getByte(readerIndex);
        final byte type = in.getByte(readerIndex + 1);
        if (isPacket(magic)) {
            if (isHeartPacket(type)){
                dispatchToHeartPacket(ctx);
            }else{
                dispatchToPacket(ctx);
            }
        } else if (isHttp(magic, type)) {
            dispatchToHttpPacket(ctx);
        } else {
            in.clear();
            ctx.close();
        }

    }

    private void dispatchToPacket(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(new DefaultMasterPacketHandler(context));
        // 将所有所需的ChannelHandler添加到pipeline之后，一定要将自身移除掉
        // 否则该Channel之后的请求仍会重新执行协议的分发，而这是要避免的
        pipeline.remove(this);
        // 将channelActive事件传递到PacketHandler
        ctx.fireChannelActive();
    }

    private void dispatchToHeartPacket(ChannelHandlerContext ctx){
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new IdleStateChecker(context.getConfig().getInt("idle.time", 60)));
        pipeline.addLast(new PacketMessageCodec());
        pipeline.addLast(new DefaultMasterPacketHandler(context));
        // 将所有所需的ChannelHandler添加到pipeline之后，一定要将自身移除掉
        // 否则该Channel之后的请求仍会重新执行协议的分发，而这是要避免的
        pipeline.remove(this);
        // 将channelActive事件传递到PacketHandler
        ctx.fireChannelActive();
    }

    private void dispatchToHttpPacket(ChannelHandlerContext ctx){
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        // aggregate HttpRequest/HttpContent/LastHttpContent to FullHttpRequest
        pipeline.addLast(new HttpObjectAggregator(8096));
        pipeline.addLast(new HttpHandler(context));
        pipeline.remove(this);
        // 将channelActive事件传递到HttpHandler
        ctx.fireChannelActive();
    }

    private static boolean isHttp(byte magic, byte type) {
        return magic == 'G' && type == 'E' || // GET
                    magic == 'P' && type == 'O' || // POST
                    magic == 'P' && type == 'U' || // PUT
                    magic == 'H' && type == 'E' || // HEAD
                    magic == 'O' && type == 'P' || // OPTIONS
                    magic == 'P' && type == 'A' || // PATCH
                    magic == 'D' && type == 'E' || // DELETE
                    magic == 'T' && type == 'R' || // TRACE
                    magic == 'C' && type == 'O';   // CONNECT
    }

    private boolean isPacket(byte magic) {
        return magic == Packet.MAGIC_CODE;
    }

    private boolean isHeartPacket(byte type) {
        return type == 0x00;
    }
}
